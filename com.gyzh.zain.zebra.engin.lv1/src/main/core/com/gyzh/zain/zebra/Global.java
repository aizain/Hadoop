package com.gyzh.zain.zebra;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.gyzh.zain.zebra.domain.Engin2Info;

/**
 * 程序的配置文件
 * 后期会用zookeper替代
 * @author zain
 * 17/02/12
 */
public class Global {
    /**
     * 扫描文件的基路径
     */
     // private static final String baseDir = "E:\\data"; // windows
     private static final String baseDir = "/usr/zain/zebra/engin1/data"; // linux
    
    
    /**
     * 存储扫描到的文件的阻塞式队列
     */
    private static final BlockingQueue<File> fileQueue = new LinkedBlockingQueue<>();
    /**
     * 文件切块的线程池
     */
    private static final ExecutorService file2BlockService = Executors.newCachedThreadPool();
    /**
     * 文件切块时，块的大小
     */
    private static final int blockSize = 1024 * 1024 * 2;
    /**
     * 块转行线程池
     */
    private static final ExecutorService block2LineService = Executors.newCachedThreadPool();
    /**
     * 存储拼完时间片的行的队列
     */
    private static final BlockingQueue<String> lineQueue = new LinkedBlockingQueue<>();

    /**
     * 存储二级引擎的map
     */
    private static final Map<String, Engin2Info> e2Map = new HashMap<>();
    
    static {
        // e2Map.put("1", new Engin2Info("127.0.0.1", 9999)); // windows
        // e2Map.put("2", new Engin2Info("127.0.0.1", 9999)); // windows
        
        e2Map.put("1", new Engin2Info("10.19.177.182", 8899)); // linux
        // e2Map.put("2", new Engin2Info("127.0.0.1", 9999)); // linux
    }
    
    public static Map<String, Engin2Info> getE2map() {
        return e2Map;
    }
    public static String getBasedir() {
        return baseDir;
    }
    public static BlockingQueue<File> getFilequeue() {
        return fileQueue;
    }
    public static ExecutorService getFile2blockservice() {
        return file2BlockService;
    }
    public static int getBlocksize() {
        return blockSize;
    }
    public static ExecutorService getBlock2lineservice() {
        return block2LineService;
    }
    public static BlockingQueue<String> getLinequeue() {
        return lineQueue;
    }
}
