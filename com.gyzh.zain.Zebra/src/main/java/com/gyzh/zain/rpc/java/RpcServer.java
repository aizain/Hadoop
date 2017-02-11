package com.gyzh.zain.rpc.java;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收端
 * @author zain
 * 17/02/11
 */
public class RpcServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(9999));
        Socket s = ss.accept();
        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();
        // 1.接收客户端发送的数据
        ObjectInputStream ois = new ObjectInputStream(is);
        // 2.反序列化回对象
        Req req = (Req) ois.readObject();
        // 3.获取参数调用真正的方法
        int num1 = req.getNum1();
        int num2 = req.getNum2();
        // 4.将结果存入对象，序列化
        Res res = new Res();
        res.setResult(add(num1, num2));
        // 5.发送给调用方
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(res);
        // 6.关闭资源
        oos.close();
        ois.close();
        s.close();
        ss.close();
    }
    
    public static int add(int num1, int num2) {
        return num1 + num2;
    }
}
