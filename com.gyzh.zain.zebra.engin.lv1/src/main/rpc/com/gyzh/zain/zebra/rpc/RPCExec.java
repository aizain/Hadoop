package com.gyzh.zain.zebra.rpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.gyzh.zain.zebra.Global;
import com.gyzh.zain.zebra.domain.Engin2Info;
import com.gyzh.zain.zebra.domain.HTTPAPPHost;
import com.gyzh.zain.zebra.domain.RPCInfo.RPCReq;
import com.gyzh.zain.zebra.domain.RPCInfo.RPCRes;
import com.gyzh.zain.zebra.domain.RPCInfo.RPCReq.RPCHah;
import com.gyzh.zain.zebra.domain.RPCInfo.ReduceService;
import com.gyzh.zain.zebra.domain.RPCInfo.ReduceService.BlockingInterface;
import com.gyzh.zain.zebra.net.NIOClient;

/**
 * RPC执行器
 * 用于序列化发送网络数据包
 * @author zain
 * 17/02/18
 */
public class RPCExec {
    
    private static final Logger logger = Logger.getLogger(RPCExec.class);
    
    /**
     * 序列化并发送
     * @param map
     */
    public static void seriaAndSend(Map<String, List<HTTPAPPHost>> map) {
        // 1.遍历Map
        try {
            for (Map.Entry<String, List<HTTPAPPHost>> entry : map.entrySet()) {
                // 2. 将List序列化
                List<HTTPAPPHost> list = entry.getValue();
                List<RPCHah> rpcList = new ArrayList<>();
                for (HTTPAPPHost hah : list) { // 转换成序列化格式的对象
                    RPCHah rpchah = RPCHah.newBuilder()
                            .setCellid(hah.getCellid())
                            .setAppType(hah.getAppType())
                            .setAppSubtype(hah.getAppSubtype())
                            .setUserIP(hah.getUserIP())
                            .setUserPort(hah.getUserPort())
                            .setAppServerIP(hah.getAppServerIP())
                            .setAppServerPort(hah.getAppServerPort())
                            .setTrafficUL(hah.getTrafficUL())
                            .setTrafficDL(hah.getTrafficDL())
                            .setRetranUL(hah.getRetranUL())
                            .setRetranDL(hah.getRetranDL())
                            .setHost(hah.getHost())
                            .setReportTime(hah.getReportTime().getTime())
                            .setSliceType(hah.getSliceType())
                            .setSlice(hah.getSlice())
                            .setTransDelay(hah.getTransDelay())
                            .setShufflekey(hah.getShufflekey())
                            .setAttempts(hah.getAttempts())
                            .setAccepts(hah.getAccepts())
                            .setFailCount(hah.getFailCount())
                            .build();
                    rpcList.add(rpchah);
                }
                // 待发送的序列化对象
                RPCReq req = RPCReq.newBuilder().addAllHahs(rpcList).setMname("reduce").build();
                // 3. 找到shuffleKey对应的二级引擎
                Engin2Info e2info = Global.getE2map().get(entry.getKey());
                // 4.调用远程方法 -- 通过网络模块 -- 将数据发送到二级引擎
                // 获取存根
                BlockingInterface service = ReduceService.newBlockingStub(new BlockingRpcChannel() {
                    // 自己定义调用远程方法时使用的通道
                    // 在此处需要写调用网络模块的代码
                    @Override
                    public Message callBlockingMethod(MethodDescriptor arg0, RpcController arg1, Message arg2, Message arg3)
                            throws ServiceException {
                        // 实现真正的远程调用
                        try {
                            byte[] array = req.toByteArray();
                            e2info.getDataQueue().put(array);
                            logger.info("调用NIOClient，准备发送数据");
                            NIOClient.sendData(e2info);
                        } catch (InterruptedException e) {
                            logger.error("执行RPCExec出错", e);
                            throw new RuntimeException(e);
                        }
                        return null;
                    }
                });
                // 调用远程方法
                service.reduce(null, req);
            }
        } catch (Exception e) {
            logger.error("执行RPCExec出错", e);
        }
    };
}


