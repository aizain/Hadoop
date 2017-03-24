package com.gyzh.zain.zebra.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gyzh.zain.zebra.Global;
import com.gyzh.zain.zebra.rpc.RPCExec;

/**
 * 网络模块
 * 服务端
 * 接收传过来的数据
 * 
 * @author zain
 * 17/02/19
 */
public class NIOServer implements Runnable {
    
    private static Logger logger = Logger.getLogger(NIOServer.class);
    private static Selector selc = null;
    
    static {
        try {
            selc = Selector.open();
        } catch (IOException e) {
            logger.error(e);
        }
    }
    
    
    @Override
    public void run() {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress("10.19.177.182", Global.getPort()));
            ssc.register(selc, SelectionKey.OP_ACCEPT);
            logger.info("监听开启： " + ssc.toString());
            
            
            while (true) {
                selc.select(1 * 1000);
                Set<SelectionKey> set = selc.selectedKeys();
                Iterator<SelectionKey> it = set.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    
                    if (key.isAcceptable()) {
                        ServerSocketChannel sscx = (ServerSocketChannel) key.channel();
                        SocketChannel sc = null;
                        while(sc==null) sc = sscx.accept();
                        sc.configureBlocking(false);
                        sc.register(selc, SelectionKey.OP_READ);
                    }
                    
                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer temp = ByteBuffer.allocate(1);
                        String head = "";
                        while (!head.endsWith("\r\n")) {
                            sc.read(temp);
                            temp.flip();
                            head += (char) temp.get(0);
                            temp.clear();
                        }
                        int len = Integer.parseInt(head.substring(0, head.length()-2));
                        ByteBuffer bodyBuf = ByteBuffer.allocate(len);
                        while (bodyBuf.hasRemaining()) {
                            sc.read(bodyBuf);
                        }
                        byte[] data = bodyBuf.array();
                        logger.info("收到了客户端发送的数据： " + data.length);
                        RPCExec.execData(data);
                    }
                    
                    it.remove();
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

}
