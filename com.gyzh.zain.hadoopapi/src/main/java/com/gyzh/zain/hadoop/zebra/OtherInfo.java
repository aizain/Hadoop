package com.gyzh.zain.hadoop.zebra;

public class OtherInfo {
    private String reportTimeStr;
    private Integer appTypeCode = 0;
    private Long procdureStartTime = 0L;
    private Long procdureEndTime = 0L;
    private Long trafficUL = 0L;
    private Long trafficDL = 0L;
    private Long retranUL = 0L;
    private Long retranDL = 0L;
    private Integer transStatus = -1;
    private String interruptType = "fail";
    @Override
    public String toString() {
        return reportTimeStr + "|" + appTypeCode + "|" + procdureStartTime + "|" + procdureEndTime + "|" + trafficUL
                + "|" + trafficDL + "|" + retranUL + "|" + retranDL + "|" + transStatus + "|" + interruptType;
    }
    public String getReportTimeStr() {
        return reportTimeStr;
    }
    public void setReportTimeStr(String reportTimeStr) {
        this.reportTimeStr = reportTimeStr;
    }
    public Integer getAppTypeCode() {
        return appTypeCode;
    }
    public void setAppTypeCode(Integer appTypeCode) {
        this.appTypeCode = appTypeCode;
    }
    public Long getProcdureStartTime() {
        return procdureStartTime;
    }
    public void setProcdureStartTime(Long procdureStartTime) {
        this.procdureStartTime = procdureStartTime;
    }
    public Long getProcdureEndTime() {
        return procdureEndTime;
    }
    public void setProcdureEndTime(Long procdureEndTime) {
        this.procdureEndTime = procdureEndTime;
    }
    public Long getTrafficUL() {
        return trafficUL;
    }
    public void setTrafficUL(Long trafficUL) {
        this.trafficUL = trafficUL;
    }
    public Long getTrafficDL() {
        return trafficDL;
    }
    public void setTrafficDL(Long trafficDL) {
        this.trafficDL = trafficDL;
    }
    public Long getRetranUL() {
        return retranUL;
    }
    public void setRetranUL(Long retranUL) {
        this.retranUL = retranUL;
    }
    public Long getRetranDL() {
        return retranDL;
    }
    public void setRetranDL(Long retranDL) {
        this.retranDL = retranDL;
    }
    public Integer getTransStatus() {
        return transStatus;
    }
    public void setTransStatus(Integer transStatus) {
        this.transStatus = transStatus;
    }
    public String getInterruptType() {
        return interruptType;
    }
    public void setInterruptType(String interruptType) {
        this.interruptType = interruptType;
    }
    
}
