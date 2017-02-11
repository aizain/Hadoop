package com.gyzh.zain.rpc.gpf;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.protobuf.BlockingService;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.gyzh.zain.rpc.gpf.MathServicex.MathService;
import com.gyzh.zain.rpc.gpf.MathServicex.Req;
import com.gyzh.zain.rpc.gpf.MathServicex.Res;

/**
 * 服务端
 * @author zain
 * 17/02/11
 */
public class GRpcService {
    public static void main(String[] args) throws Exception {
        // 1.获取客户端发送过来的请求对象
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(9999));
        Socket s = ss.accept();
        InputStream in = s.getInputStream();
        Req req = Req.parseFrom(in);
        s.shutdownInput();
        // 2.创建真正的远程调用对象，客户端发送过来的调用请求都用这个对象来处理
        MyMathService service = new MyMathService();
        // 3.将真正的service包装一下，可以实现通过方法名来调用真正的方法
        BlockingService bService = MathService.newReflectiveBlockingService(service);
        // 4.获取要调用的方法名，通过bService调用
        String mname = req.getMname();
        MethodDescriptor md = bService.getDescriptorForType().findMethodByName(mname);
        // 5.执行方法
        Res res = (Res) bService.callBlockingMethod(md, null, req);
        // 6.将响应对象发送回客户端
        OutputStream os = s.getOutputStream();
        res.writeTo(os);
        s.shutdownOutput();
        
        os.close();
        in.close();
        s.close();
        ss.close();
    }
}

class MyMathService implements MathServicex.MathService.BlockingInterface {
    @Override
    public Res add(RpcController controller, Req request) throws ServiceException {
        int reuslt = request.getNum1() + request.getNum2();
        Res res = Res.newBuilder().setResult(reuslt).build();
        return res;
    }
    
}
