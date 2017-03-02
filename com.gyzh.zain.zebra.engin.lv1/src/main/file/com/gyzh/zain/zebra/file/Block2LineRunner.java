package com.gyzh.zain.zebra.file;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.gyzh.zain.zebra.Global;

/**
 * 将块切成行
 * @author zain
 * 17/02/12
 */
public class Block2LineRunner implements Runnable {
    private static final Logger logger = Logger.getLogger(Block2LineRunner.class);
    private File f;
    private byte[] block;
    public Block2LineRunner(File f, byte[] block) {
        this.setF(f);
        this.setBlock(block);
    }

    @Override
    public void run() {
        try {
            // 从文件名中截取出时间片信息
            String time = f.getName().split("_")[1];
            String reportTimeStr = time.substring(0, time.length() - 4) + "0000";
            
            ByteArrayInputStream bais = new ByteArrayInputStream(block);
            BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
            
            String line = null;
            int count = 0;
            while (null != (line = reader.readLine())) {
                // 在line之前拼接数据产生的时间片信息
                line = reportTimeStr + "|" + line;
                Global.getLinequeue().put(line);
                logger.debug(line);
                count++;
            }
            
            logger.debug("读取到的总行数： " + count);
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public byte[] getBlock() {
        return block;
    }
    public void setBlock(byte[] block) {
        this.block = block;
    }
    public File getF() {
        return f;
    }
    public void setF(File f) {
        this.f = f;
    }
}
