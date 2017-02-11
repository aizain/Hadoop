package com.gyzh.zain.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 原子性-问题示例
 * @author zain
 * 17/02/11
 */
public class AtomicErr {
    public static int i = 0;
    public static void main(String[] args) throws Exception {
        CountDownLatch cdl = new CountDownLatch(2);
        new Thread(new At(cdl)).start();
        new Thread(new At(cdl)).start();
        cdl.await();
        System.out.println(i);
    }
    
}

class At implements Runnable {
    private CountDownLatch cdl;
    public At(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        for (int i=0; i<10000; i++) {
            AtomicErr.i++;
        }
        cdl.countDown();
    }
}