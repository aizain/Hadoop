package com.gyzh.zain.rpc.gpf;

import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.google.protobuf.Descriptors.MethodDescriptor;
import com.gyzh.zain.rpc.gpf.MathServicex.MathService;
import com.gyzh.zain.rpc.gpf.MathServicex.Req;
import com.gyzh.zain.rpc.gpf.MathServicex.Res;

/**
 * 客户端
 * @author zain
 * 17/02/11
 */
public class GRpcClient {
    public static void main(String[] args) {
        // 1.获取要调用的远程对象的本地存根
        // 以后想调远程的任何方法都找这个存根来调用
        // 调用存根的任何方法，他都会去调用你传入的RpcChannel的callMethod方法
        // 需要自己实现callMethod的实现
        // 存根，代表远程的对象信息
        MathService stub = MathService.newStub(new MyChannel());
        // 2.准备请求对象
        Req req = Req.newBuilder()
                .setNum1(1).setNum2(2).setMname("add")
                .build();
        // 3.调用存根中的方法
        // controller, req 请求对象, 回调函数
        stub.add(null, req, new RpcCallback<MathServicex.Res>() {
            @Override
            public void run(Res res) {
                System.out.println(res.getResult());
            }
        });
    }
}

class MyChannel implements RpcChannel {
    @Override
    public void callMethod(MethodDescriptor md, 
            RpcController rc, Message req, Message res,
            RpcCallback<Message> callback) {
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress("127.0.0.1", 9999));
            // 获取指向远程服务器的输出流
            OutputStream os = s.getOutputStream();
            // 序列化请求对象
            byte[] bs = req.toByteArray();
            // 进行发送
            os.write(bs);
            s.shutdownOutput();
            // 获取返回的结果
            InputStream is = s.getInputStream();
            Res resX = Res.parseFrom(is);
            s.shutdownInput();
            // 回调方法
            callback.run(resX);
            
            os.close();
            is.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
