package com.gyzh.zain.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class TCPServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(9999));
        ss.accept();
        System.out.println("接收到了客户端");
        
        while (true) {
        }
    }

}
