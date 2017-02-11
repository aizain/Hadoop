package com.gyzh.zain.concurrent;

/**
 * 锁
 * @author zain
 * 17/02/06
 */
public class LockDemo1 {
    public static void main(String[] args) {
        Person p = new Person("李雷", "男");
        new Thread(new P1(p)).start();
        new Thread(new L1(p)).start();
    }
}

class L1 implements Runnable {
    private Person p;
    public L1(Person p) {
        this.p = p;
    }
    @Override
    public void run() {
        while (true) {
            if ("李雷".equals(p.getName())) {
                synchronized (p) {
                    p.setName("韩梅梅");
                    p.setGender("女");
                    try {
                        p.notify();
                        p.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                synchronized (p) {
                    p.setName("李雷");
                    p.setGender("男");
                    try {
                        p.notify();
                        p.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } 
            
        }
    }
}
class P1 implements Runnable {
    private Person p;
    public P1(Person p) {
        this.p = p;
    }
    @Override
    public void run() {
        while (true) {
            synchronized (p) {
                System.out.println(p);
                try {
                    p.notify();
                    p.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



class Person {
    private String name;
    private String gender;
    public Person() {
    }
    public Person(String name, String gender) {
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
