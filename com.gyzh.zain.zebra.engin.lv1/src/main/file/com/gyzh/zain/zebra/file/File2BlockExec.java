package com.gyzh.zain.zebra.file;

import java.io.File;

import com.gyzh.zain.zebra.Global;

/**
 * 对文件分线程处理，并切块
 * @author zain
 * 17/02/12
 */
public class File2BlockExec implements Runnable {

    @Override
    public void run() {
        try {
            while (true) {
                // 从文件队列中拿出每一个文件
                File f = Global.getFilequeue().take();
                // 开启线程进行切块的操作
                Global.getFile2blockservice().execute(new File2BlockRunner(f));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
