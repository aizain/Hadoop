package com.gyzh.zain.zebra.file;

import java.io.File;

import org.apache.log4j.Logger;

import com.gyzh.zain.zebra.Global;

/**
 * 文件收集器
 * 负责扫描指定目录下的内容，
 * 根据ctr文件扫描到csv文件，
 * 将扫描到的文件存储起来，等待其他线程处理
 * @author zain
 * 17/02/12
 */
public class FileCollector implements Runnable {
    
    private static final Logger logger = Logger.getLogger(FileCollector.class);
    
    @Override
    public void run() {
        Thread.currentThread().setName("FileCollectorThread");
        try {
            while (true) {
                System.out.println("开始扫描文件");
                // 1.获取根路径下的所有File对象
                File baseDir = new File(Global.getBasedir());
                File[] fs = baseDir.listFiles();
                // 2.遍历
                for (File f : fs) {
                    if (f.isDirectory()) {
                        // 如果是文件夹就不处理
                        continue;
                    } else {
                        // 如果是个文件，判断是不是ctr文件
                        if (f.getName().endsWith(".ctr")) {
                            // 如果有ctr，则找到对应的csv存储起来
                            String csvPath = f.getPath().substring(0, 
                                    f.getPath().length() - ".ctr".length()) + ".csv";
                            File csvFile = new File(csvPath);
                            Global.getFilequeue().put(csvFile);
                            logger.info("将文件加入FileQueue: " + csvFile.getName());
                            // 删除ctr文件防止重复处理
                            f.delete();
                        } else {
                            // 其他文件不处理
                            continue;
                        }
                    }
                }
                System.out.println("扫描文件结束");
                // 每10秒钟，重复扫描一次
                Thread.sleep(10000);
            } // end while
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
