package com.gyzh.zain.zebra.domain;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 二级引擎相关信息
 * @author zain
 * 17/02/18
 */
public class Engin2Info {
    private String ip;
    private int port;
    private static BlockingQueue<byte[]> dataQueue = new LinkedBlockingQueue<>();
    
    @Override
    public boolean equals(Object obj) {
        Engin2Info target = (Engin2Info) obj;
        if (target.getIp().equals(this.getIp()) && 
                target.getPort() == this.getPort()) {
            return true;
        }
        return false;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ip == null) ? 0 : this.ip.hashCode());
        result = prime * result + port;
        return result;
    }
    
    public Engin2Info(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public static BlockingQueue<byte[]> getDataQueue() {
        return dataQueue;
    }
    public static void setDataQueue(BlockingQueue<byte[]> dataQueue) {
        Engin2Info.dataQueue = dataQueue;
    }
}
