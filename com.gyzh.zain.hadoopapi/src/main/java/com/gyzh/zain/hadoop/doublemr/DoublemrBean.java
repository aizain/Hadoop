package com.gyzh.zain.hadoop.doublemr;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class DoublemrBean implements WritableComparable<DoublemrBean> {
    private String name;
    private int profit;
    
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(profit);
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        name = in.readUTF();
        profit = in.readInt();
    }
    @Override
    public int compareTo(DoublemrBean o) {
        // 升序
        return this.profit - o.getProfit();
    }
    
    @Override
    public String toString() {
        return name + " " + profit;
    }
    public int getProfit() {
        return profit;
    }
    public void setProfit(int profit) {
        this.profit = profit;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
