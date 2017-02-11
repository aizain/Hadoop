package com.gyzh.zain.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 服务端
 * @author zain
 * 17/02/05
 */
public class SscSelector {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open(); // 开启一个channel 
        ssc.bind(new InetSocketAddress("127.0.0.1", 9999)); // 设置服务器监听
        ssc.configureBlocking(false); // 设置channel为非阻塞
        Selector selc = Selector.open(); // 开启选择器
        ssc.register(selc, SelectionKey.OP_ACCEPT); // 将channel注册到选择器，注册为connect类型。（此处貌似用了独裁者模式）
        
        while (true) { // 死循环处理连接
            selc.select(); // 使用选择器，选择准备好的channel，将其放入选择器的第二个内含集合中，该方法阻塞
            Set<SelectionKey> set = selc.selectedKeys(); // 获取选择器中第二个内含集合中的，所有的selectedKey
            Iterator<SelectionKey> it = set.iterator(); // 获取迭代器
            
            while (it.hasNext()) {
                SelectionKey sk = it.next();
                
                if (sk.isAcceptable()) { // 如果该selectionKey是接收连接类型的
                    ServerSocketChannel sscx = (ServerSocketChannel) sk.channel(); // 获取selectionKey对应的channel
                    SocketChannel sc = sscx.accept(); // 获取服务端接收到的socket的通道
                    sc.configureBlocking(false); // 设置channel为非阻塞
                    // 此处使用了位运算，源码使用的也是位运算，所以可以同时包含多模式。 0101
                    sc.register(selc, SelectionKey.OP_READ | SelectionKey.OP_WRITE); // 注册接收到的socket通道为读和写模式
                }
                
                if (sk.isReadable()) { // 如果该slectionKey是读类型的
                    SocketChannel sc = (SocketChannel) sk.channel();
                    String head = "";
                    ByteBuffer buf = ByteBuffer.allocate(1);
                    while (!head.endsWith("\r\n")) { // 读取头部分
                        sc.read(buf);
                        buf.flip(); // 反转buffer，以便从buffer中读取数据
                        head += (char) buf.get();
                        buf.clear();
                    }
                    
                    int size = Integer.parseInt(head.substring(0, head.length()-2));
                    buf = ByteBuffer.allocate(size); // 创建一个buffer用于接收数据
                    while (buf.hasRemaining()) { // 读数据到buffer中 
                        sc.read(buf);
                    }; 
                    
                    System.out.println(new String(buf.array()));
                    // 重新注册，使用位运算，去掉读监听
                    sc.register(selc, sk.interestOps() & ~SelectionKey.OP_READ);
                }
                
                it.remove(); // 处理完该selectionKey，就从选择器的第二个内含集合中去掉
            }
        }
    }
}
