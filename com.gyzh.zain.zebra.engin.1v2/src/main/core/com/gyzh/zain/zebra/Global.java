package com.gyzh.zain.zebra;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.gyzh.zain.zebra.domain.HTTPAPPHost;

/**
 * 程序的配置文件
 * 后期会用zookeper替代
 * @author zain
 * 17/02/12
 */
public class Global {
    
    /**
     * 监听的端口号
     */
    private static final int port = 9999;
    /**
     * 存放接收到的数据
     */
    private static final BlockingQueue<HTTPAPPHost> dataQueue = new LinkedBlockingDeque<>();
    
    /**
     * 数据库连接信息
     */
    private final static String jdbcurl = "jdbc:mysql:///zebra";
    private final static String jdbcuser = "root";
    private final static String jdbcroot = "123456";
    

    public static int getPort() {
        return port;
    }

    public static BlockingQueue<HTTPAPPHost> getDataqueue() {
        return dataQueue;
    }
}
