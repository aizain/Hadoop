package com.gyzh.zain.zebra;

import com.gyzh.zain.zebra.exec.Exec;
import com.gyzh.zain.zebra.net.NIOServer;

/**
 * 启动文件
 * @author zain
 * 17/02/12
 */
public class Start {
    public static void main(String[] args) {
        // 启动网络模块
        System.out.println("开启网络模块");
        new Thread(new NIOServer()).start();
        // 启动执行器
        System.out.println("启动执行器");
        new Thread(new Exec()).start();
    }
}
