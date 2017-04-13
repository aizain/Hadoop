package com.gyzh.zain.hbase.dao;

public class NetFlowInfo {
	private String time;
	private String urlname;
	private String uvid;
	private String sid;
	private String scount;
	private String stime;
	private String cip;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUrlname() {
		return urlname;
	}
	public void setUrlname(String urlname) {
		this.urlname = urlname;
	}
	public String getUvid() {
		return uvid;
	}
	public void setUvid(String uvid) {
		this.uvid = uvid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getScount() {
		return scount;
	}
	public void setScount(String scount) {
		this.scount = scount;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getCip() {
		return cip;
	}
	public void setCip(String cip) {
		this.cip = cip;
	}
	
	public String getRowKey(){
		String rk = time+"_"+sid+"_"+uvid+"_"+cip+"_"+Math.round(Math.random()*1000000);
		return rk;
	}
}
