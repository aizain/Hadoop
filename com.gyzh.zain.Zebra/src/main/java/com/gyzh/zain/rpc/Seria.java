package com.gyzh.zain.rpc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化
 * @author zain
 * 17/02/11
 */
public class Seria {
    public static void main(String[] args) throws Exception, IOException {
        Persion1 p1 = new Persion1();
        p1.setName("zain");
        p1.setAge(23);
        p1.setPwd("sadsads");
        
        String path = "1.data";
        ObjectOutputStream ops = new ObjectOutputStream(new FileOutputStream(path));
        ops.writeObject(p1);
        ops.close();
        
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        Persion1 p2 = (Persion1) ois.readObject();
        ois.close();
        
        System.out.println(p2);
    }
}

class Persion1 implements Serializable {
    /**
     * 序列化时生成的唯一编号
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private transient String pwd;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    @Override
    public String toString() {
        return "Persion1 [name=" + name + ", age=" + age + ", pwd=" + pwd + "]";
    }
}