package com.gyzh.zain.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket();
        s.connect(new InetSocketAddress("127.0.0.1", 9999));
        
        OutputStream out = s.getOutputStream();
        for (int i=0; i<Integer.MAX_VALUE; i++) {
            System.out.println(i);
            out.write("a".getBytes());
        }
        out.write("a".getBytes());
        
        System.out.println("连接了服务器");
    }
    
}
