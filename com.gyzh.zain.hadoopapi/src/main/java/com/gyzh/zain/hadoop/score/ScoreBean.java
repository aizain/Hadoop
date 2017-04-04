package com.gyzh.zain.hadoop.score;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class ScoreBean implements Writable {
    private String name;
    private int chinese;
    private int english;
    private int math;
    
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(chinese);
        out.writeInt(english);
        out.writeInt(math);
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        name = in.readUTF();
        chinese = in.readInt();
        english = in.readInt();
        math = in.readInt();
    }
    
    @Override
    public String toString() {
        return "ScoreBean [name=" + name + ", chinese=" + chinese + ", english=" + english + ", math=" + math + "]";
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getChinese() {
        return chinese;
    }
    public void setChinese(int chinese) {
        this.chinese = chinese;
    }
    public int getEnglish() {
        return english;
    }
    public void setEnglish(int english) {
        this.english = english;
    }
    public int getMath() {
        return math;
    }
    public void setMath(int math) {
        this.math = math;
    }
}
