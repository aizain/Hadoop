package com.gyzh.zain.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端
 * @author zain
 * 17/02/05
 */
public class ScSelector {
    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open(); // 开启一个channel
        sc.configureBlocking(false); // 设置channel为非阻塞
        sc.connect(new InetSocketAddress("127.0.0.1", 9999)); // 客户的开始连接，非阻塞
        Selector selc = Selector.open(); // 开启选择器
        sc.register(selc, SelectionKey.OP_CONNECT); // 将channel注册到选择器，注册为connect类型。（此处貌似用了独裁者模式）
        
        while (true) { // 死循环处理连接
            selc.select(); // 使用选择器，选择准备好的channel，将其放入选择器的第二个内含集合中，该方法阻塞
            Set<SelectionKey> set = selc.selectedKeys(); // 获取选择器中第二个内含集合中的，所有的selectedKey
            Iterator<SelectionKey> it = set.iterator(); // 获取迭代器
            
            while (it.hasNext()) { // 遍历全部准备好的selectionKey，每个selectionKey对应一个channel
                SelectionKey sk = it.next();
                
                if (sk.isConnectable()) { // 如果该selectionKey是连接类型的
                    SocketChannel scx = (SocketChannel) sk.channel(); // 获取selectionKey对应的channel
                    scx.finishConnect(); // 结束socket连接
                    // 此处使用了位运算，源码使用的也是位运算，所以可以同时包含多模式。 0101
                    scx.register(selc, SelectionKey.OP_READ | SelectionKey.OP_WRITE); // 注册接收到的socket通道为读和写模式
                }
                
                if (sk.isWritable()) { // 如果该slectionKey是写类型的
                    SocketChannel scx = (SocketChannel) sk.channel(); // 获取selectionKey对应的channel
                    byte[] body = "adsdadsdsa".getBytes();
                    byte[] head = (body.length + "\r\n").getBytes();
                    ByteBuffer headBuf = ByteBuffer.wrap(head); // 用buffer保证要写的内容，请求头
                    ByteBuffer bodyBuf = ByteBuffer.wrap(body); // 用buffer保证要写的内容
                    while (bodyBuf.hasRemaining()) { // 如果还有内容就继续写
                        scx.write(new ByteBuffer[] {headBuf, bodyBuf}); // 将buffer中的内容写入socket，非阻塞
                    }
                    // 重新注册，使用位运算，去掉读监听
                    scx.register(selc, sk.interestOps() & ~SelectionKey.OP_READ);
                }
                
                it.remove(); // 处理完该selectionKey，就从选择器的第二个内含集合中去掉
            }
        }
    }
}
