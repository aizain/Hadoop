package com.gyzh.zain.zebra.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.gyzh.zain.zebra.domain.Engin2Info;

/**
 * 网络模块
 * 
 * 只负责发送数据
 * @author zain
 * 17/02/19
 */
public class NIOClient implements Runnable {
    
    private static Logger logger = Logger.getLogger(NIOClient.class);
    private static Selector selc = null;
    /**
     * 缓存连接，复用
     */
    private static Map<Engin2Info, SocketChannel> channelMap = null;
    
    static {
        try {
            channelMap = new HashMap<>();
            selc = Selector.open();
        } catch (IOException e) {
            logger.error(e);
        }
    }
    
    /**
     * 发送数据
     * @param e2info
     */
    public static void sendData(Engin2Info e2info) {
        try {
            if (channelMap.containsKey(e2info)) {
                // 之前已经创建过到此远程主机的通道，可以复用
                SocketChannel sc = channelMap.get(e2info);
                sc.register(selc, SelectionKey.OP_WRITE, e2info);
            } else {
                // 之前没有创建过到此远程主机的通道，需要建立
                SocketChannel sc = SocketChannel.open();
                sc.configureBlocking(false);
                sc.connect(new InetSocketAddress(e2info.getIp(), e2info.getPort()));
                sc.register(selc, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE, e2info);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                selc.select(1 * 1000); // 设置超时时间 ms
                Set<SelectionKey> set = selc.selectedKeys();
                Iterator<SelectionKey> it = set.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    
                    if (key.isConnectable()) {
                        // 完成连接
                        SocketChannel sc = (SocketChannel) key.channel();
                        while (!sc.finishConnect()) {}
                        // 保存到map中，为了可以复用
                        Engin2Info e2info = (Engin2Info) key.attachment();
                        channelMap.put(e2info, sc);
                        logger.info("创建了连接到远程主机的通道： " + e2info.getIp() + " : " + e2info.getPort());
                        // 清除注册操作
                        sc.register(selc, key.interestOps() & (~SelectionKey.OP_CONNECT), e2info);
                    }
                    
                    if (key.isWritable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        Engin2Info e2info = (Engin2Info) key.attachment();
                        byte[] data = e2info.getDataQueue().poll(1, TimeUnit.SECONDS);
                        ByteBuffer headBuf = ByteBuffer.wrap((data.length + "\r\n").getBytes());
                        ByteBuffer bodyBuf = ByteBuffer.wrap(data);
                        while (bodyBuf.hasRemaining()) {
                            sc.write(new ByteBuffer[] {headBuf, bodyBuf});
                        }
                        logger.info("向远程主机发送了数据： " + data.length + "字节");
                        sc.register(selc, key.interestOps() & (~SelectionKey.OP_WRITE));
                    }
                    
                    it.remove();
                }
                
            }
            
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
