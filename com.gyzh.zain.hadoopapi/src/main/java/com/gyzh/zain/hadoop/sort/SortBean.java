package com.gyzh.zain.hadoop.sort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class SortBean implements WritableComparable<SortBean> {
    private String name;
    private int score;
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(score);
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        this.name = in.readUTF();
        this.score = in.readInt();
    }
    @Override
    public int compareTo(SortBean o) {
        // 升序
        // return this.score - o.getScore();
        // 降序
        return o.score - this.getScore();
    }
    
    @Override
    public String toString() {
        return "SortBean [name=" + name + ", score=" + score + "]";
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
