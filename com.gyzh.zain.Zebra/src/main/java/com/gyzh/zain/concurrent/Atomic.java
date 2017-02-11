package com.gyzh.zain.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子性-操作示例
 * @author zain
 * 17/02/11
 */
public class Atomic {
    public static AtomicInteger i = new AtomicInteger(0);
    public static void main(String[] args) throws Exception {
        CountDownLatch cdl = new CountDownLatch(2);
        new Thread(new At2(cdl)).start();
        new Thread(new At2(cdl)).start();
        cdl.await();
        System.out.println(i);
    }
    
}

class At2 implements Runnable {
    private CountDownLatch cdl;
    public At2(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        for (int i=0; i<10000; i++) {
            Atomic.i.addAndGet(1);
        }
        cdl.countDown();
    }
}