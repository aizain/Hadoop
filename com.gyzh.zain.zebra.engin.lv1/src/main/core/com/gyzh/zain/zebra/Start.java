package com.gyzh.zain.zebra;

import com.gyzh.zain.zebra.exec.Exec;
import com.gyzh.zain.zebra.file.File2BlockExec;
import com.gyzh.zain.zebra.file.FileCollector;
import com.gyzh.zain.zebra.net.NIOClient;

/**
 * 启动文件
 * @author zain
 * 17/02/12
 */
public class Start {
    public static void main(String[] args) {
        // 启动网络模块
        System.out.println("开启网络模块");
        new Thread(new NIOClient()).start();
        // 扫描文件
        System.out.println("开启文件扫描器");
        new Thread(new FileCollector()).start();
        // 启动文件切块
        System.out.println("开启文件切块");
        new Thread(new File2BlockExec()).start();
        // 启动Exec数据处理模块
        System.out.println("开启数据处理模块");
        new Thread(new Exec()).start();
    }
}
