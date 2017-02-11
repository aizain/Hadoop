package com.gyzh.zain.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁
 * @author zain
 * 17/02/06
 */
public class LockDemo2 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Person1 p = new Person1("李雷", "男");
        new Thread(new P2(p, lock)).start();
        new Thread(new L2(p, lock)).start();
    }
}

class L2 implements Runnable {
    private Person1 p;
    private Lock lock;
    public L2(Person1 p, Lock lock) {
        this.p = p;
        this.lock = lock;
    }
    @Override
    public void run() {
        while (true) {
            lock.lock();
            if ("李雷".equals(p.getName())) {
                p.setName("韩梅梅");
                p.setGender("女");
            } else {
                p.setName("李雷");
                p.setGender("男");
            } 
            lock.unlock();
        }
    }
}
class P2 implements Runnable {
    private Person1 p;
    private Lock lock;
    public P2(Person1 p, Lock lock) {
        this.p = p;
        this.lock = lock;
    }
    @Override
    public void run() {
        while (true) {
            lock.lock();
            System.out.println(p);
            lock.unlock();
        }
    }
}



class Person1 {
    private String name;
    private String gender;
    public Person1() {
    }
    public Person1(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    @Override
    public String toString() {
        return "Person [name=" + name + ", gender=" + gender + "]";
    }
}
