package com.gyzh.zain.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏
 * @author zain
 * 17/02/06
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        System.out.println("开始准备集合起始点");
        CyclicBarrier cb = new CyclicBarrier(2);
        new Thread(new T1(cb)).start();
        new Thread(new T2(cb)).start();
        System.out.println("发车结束");
    }
}

class T1 implements Runnable {
    private CyclicBarrier cb;
    public T1(CyclicBarrier cb) {
        this.cb = cb;
    }

    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("t1就位");
            cb.await();
            System.out.println("t1起跑");
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
class T2 implements Runnable {
    private CyclicBarrier cb;
    public T2(CyclicBarrier cb) {
        this.cb = cb;
    }

    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("t2就位");
            cb.await();
            System.out.println("t2起跑");
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
