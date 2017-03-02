package com.gyzh.zain.zebra.file;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;

import com.gyzh.zain.zebra.Global;


/**
 * 对文件进行切块处理
 * @author zain
 * 17/02/12
 */
public class File2BlockRunner implements Runnable {
    private static final Logger logger = Logger.getLogger(File2BlockRunner.class);
    private File f;
    public File2BlockRunner(File f) {
        this.f = f;
    }

    @SuppressWarnings("resource")
    @Override
    public void run() {
        try {
            // 分块读取数据
            ByteBuffer buf = ByteBuffer.allocate(Global.getBlocksize());
            FileChannel channel = new FileInputStream(f).getChannel();
            // 读取文件进行切块，注意边界的处理，不能出现断行
            while (channel.read(buf) != -1) { // 从文件中读取块制定大小的数据
                // 处理边界问题，从buf的最后开始向前找最近的"\r\n"，进行切割，
                // 之前的作为一块，剩余的留到下一块作为开头
                buf.flip();
                int keyPosition = -1;
                for (int i=buf.limit()-1; ; i--) {
                    if ('\n' == (char) buf.get(i)) {
                        keyPosition = i + 1;
                        break;
                    }
                }
                byte[] block = new byte[keyPosition];
                buf.get(block);
                
                // 截出单独行数据
                byte[] temp = new byte[buf.limit() - keyPosition];
                buf.get(temp);
                
                Global.getBlock2lineservice().execute(new Block2LineRunner(f, block));
                buf.clear();
                // 将单独行数据放回数组，等待下一次读取
                buf.put(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }

}
