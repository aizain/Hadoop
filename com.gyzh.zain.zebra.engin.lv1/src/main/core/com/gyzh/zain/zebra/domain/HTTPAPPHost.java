package com.gyzh.zain.zebra.domain;

import java.sql.Timestamp;

public class HTTPAPPHost {
    private String cellid = "";
    private int appType = 0;
    private int appSubtype = 0;
    private String userIP = "";
    private int userPort = 0;
    private String appServerIP = "";
    private int appServerPort = 0;
    private long trafficUL = 0;
    private long trafficDL = 0;
    private long attempts = 0;
    private long accepts = 0;
    private long failCount = 0;
    private long retranUL = 0;
    private long retranDL = 0;
    private String host = "";
    private Timestamp reportTime;
    private int sliceType = 60;
    private long slice = 0;
    private long transDelay = 0;
    private String shufflekey = "";
    @Override
    public String toString() {
        return cellid + "|" + appType + "|" + appSubtype + "|" + userIP + "|" + userPort + "|" + appServerIP + "|"
                + appServerPort + "|" + trafficUL + "|" + trafficDL + "|" + attempts + "|" + accepts + "|" + failCount
                + "|" + retranUL + "|" + retranDL + "|" + host + "|" + reportTime + "|" + sliceType + "|" + slice + "|"
                + transDelay + "|" + shufflekey;
    }
    public String getCellid() {
        return cellid;
    }
    public void setCellid(String cellid) {
        this.cellid = cellid;
    }
    public int getAppType() {
        return appType;
    }
    public void setAppType(int appType) {
        this.appType = appType;
    }
    public int getAppSubtype() {
        return appSubtype;
    }
    public void setAppSubtype(int appSubtype) {
        this.appSubtype = appSubtype;
    }
    public String getUserIP() {
        return userIP;
    }
    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }
    public int getUserPort() {
        return userPort;
    }
    public void setUserPort(int userPort) {
        this.userPort = userPort;
    }
    public String getAppServerIP() {
        return appServerIP;
    }
    public void setAppServerIP(String appServerIP) {
        this.appServerIP = appServerIP;
    }
    public int getAppServerPort() {
        return appServerPort;
    }
    public void setAppServerPort(int appServerPort) {
        this.appServerPort = appServerPort;
    }
    public long getTrafficUL() {
        return trafficUL;
    }
    public void setTrafficUL(long trafficUL) {
        this.trafficUL = trafficUL;
    }
    public long getTrafficDL() {
        return trafficDL;
    }
    public void setTrafficDL(long trafficDL) {
        this.trafficDL = trafficDL;
    }
    public long getAttempts() {
        return attempts;
    }
    public void setAttempts(long attempts) {
        this.attempts = attempts;
    }
    public long getAccepts() {
        return accepts;
    }
    public void setAccepts(long accepts) {
        this.accepts = accepts;
    }
    public long getFailCount() {
        return failCount;
    }
    public void setFailCount(long failCount) {
        this.failCount = failCount;
    }
    public long getRetranUL() {
        return retranUL;
    }
    public void setRetranUL(long retranUL) {
        this.retranUL = retranUL;
    }
    public long getRetranDL() {
        return retranDL;
    }
    public void setRetranDL(long retranDL) {
        this.retranDL = retranDL;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public Timestamp getReportTime() {
        return reportTime;
    }
    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }
    public int getSliceType() {
        return sliceType;
    }
    public void setSliceType(int sliceType) {
        this.sliceType = sliceType;
    }
    public long getTransDelay() {
        return transDelay;
    }
    public void setTransDelay(long transDelay) {
        this.transDelay = transDelay;
    }
    public String getShufflekey() {
        return shufflekey;
    }
    public void setShufflekey(String shufflekey) {
        this.shufflekey = shufflekey;
    }
    public long getSlice() {
        return slice;
    }
    public void setSlice(long slice) {
        this.slice = slice;
    }
}
