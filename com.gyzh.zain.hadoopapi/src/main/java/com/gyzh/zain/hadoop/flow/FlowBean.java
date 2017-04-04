package com.gyzh.zain.hadoop.flow;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * 流量对象
 * @author zain
 * 17/04/04
 */
public class FlowBean implements Writable {
    private String phone;
    private String addr;
    private String name;
    private int flow;
    
    /**
     * 序列化方法
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(phone);
        out.writeUTF(addr);
        out.writeUTF(name);
        out.writeInt(flow);
    }
    
    /**
     * 反序列化方法
     * 反序列化顺序要和序列化顺序保持一致
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        setPhone(in.readUTF());
        setAddr(in.readUTF());
        setName(in.readUTF());
        setFlow(in.readInt());
    }
    
    @Override
    public String toString() {
        return "FlowBean [phone=" + phone + ", addr=" + addr + ", name=" + name + ", flow=" + flow + "]";
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getFlow() {
        return flow;
    }
    public void setFlow(int flow) {
        this.flow = flow;
    }
}
