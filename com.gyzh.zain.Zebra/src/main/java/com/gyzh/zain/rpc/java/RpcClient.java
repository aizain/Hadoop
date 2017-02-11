package com.gyzh.zain.rpc.java;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 发送方
 * @author zain
 * 17/02/11
 */
public class RpcClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 9999));
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        // 1.准备参数
        Req req = new Req();
        req.setNum1(1);
        req.setNum2(2);
        // 2.序列化对象
        ObjectOutputStream out = new ObjectOutputStream(os);
        // 3.通过网络发送给被调用者
        out.writeObject(req);
        // 4.接收返回的数据
        ObjectInputStream ois = new ObjectInputStream(is);
        // 5.反序列化获取结果
        Res res = (Res) ois.readObject();
        
        System.out.println(res);
    }
}
