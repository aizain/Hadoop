package com.gyzh.zain.zebra.rpc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.protobuf.BlockingService;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.gyzh.zain.zebra.Global;
import com.gyzh.zain.zebra.domain.HTTPAPPHost;
import com.gyzh.zain.zebra.domain.RPCInfo.RPCReq;
import com.gyzh.zain.zebra.domain.RPCInfo.RPCReq.RPCHah;
import com.gyzh.zain.zebra.domain.RPCInfo.RPCRes;
import com.gyzh.zain.zebra.domain.RPCInfo.ReduceService;
import com.gyzh.zain.zebra.domain.RPCInfo.ReduceService.BlockingInterface;

/**
 * RPC执行器
 * 用于反序列化接收到的网络数据包
 * @author zain
 * 17/02/18
 */
public class RPCExec {
    
    private static final Logger logger = Logger.getLogger(RPCExec.class);
    
    /**
     * 实现远程方法的调用
     * @param data
     */
    public static void execData(byte[] data) {
        try {
            // 反序列化回客户端发过来的对象
            RPCReq req = RPCReq.parseFrom(data);
            // 创建真正被调用的远程对象
            MyReduceService rservice = new MyReduceService();
            // 将真正的远程方法对象进行包装
            BlockingService bservice = ReduceService.newReflectiveBlockingService(rservice);
            // 获取要调用的方法名称
            String mname = req.getMname();
            // 获取方法描述器
            MethodDescriptor md = bservice.getDescriptorForType().findMethodByName(mname);
            // 执行方法，获取结果
            bservice.callBlockingMethod(md, null, req);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}

/**
 * 远程方法的真正实现
 * @author zain
 * 17/02/19
 */
class MyReduceService implements BlockingInterface {
    
    private static final Logger logger = Logger.getLogger(MyReduceService.class);
    
    /**
     * 实现数据处理 -- 数据聚合
     */
    @Override
    public RPCRes reduce(RpcController controller, RPCReq request) throws ServiceException {
        try {
            List<RPCHah> list = request.getHahsList();
            
            for (RPCHah rpchah : list) { // 转换成序列化格式的对象
                HTTPAPPHost hah = new HTTPAPPHost();
                hah.setCellid(rpchah.getCellid());
                hah.setAppType(rpchah.getAppType());
                hah.setAppSubtype(rpchah.getAppSubtype());
                hah.setUserIP(rpchah.getUserIP());
                hah.setUserPort(rpchah.getUserPort());
                hah.setAppServerIP(rpchah.getAppServerIP());
                hah.setAppServerPort(rpchah.getAppServerPort());
                hah.setTrafficUL(rpchah.getTrafficUL());
                hah.setTrafficDL(rpchah.getTrafficDL());
                hah.setRetranUL(rpchah.getRetranUL());
                hah.setRetranDL(rpchah.getRetranDL());
                hah.setHost(rpchah.getHost());
                hah.setReportTime(new Timestamp(rpchah.getReportTime()));
                hah.setSliceType(rpchah.getSliceType());
                hah.setSlice(rpchah.getSlice());
                hah.setTransDelay(rpchah.getTransDelay());
                hah.setShufflekey(rpchah.getShufflekey());
                hah.setAttempts(rpchah.getAttempts());
                hah.setAccepts(rpchah.getAccepts());
                hah.setFailCount(rpchah.getFailCount());
                
                logger.debug("将反序列化的对象存储到dataQueue中" + hah);
                Global.getDataqueue().put(hah);
            }
            logger.info("将一级引擎发送的数据反序列化后，保存到队列中，总数据量： " + list.size());
        } catch (Exception e) {
            logger.error(e);
        }
        
        return null;
    }
    
}
