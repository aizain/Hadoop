package com.gyzh.zain.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁
 * @author zain
 * 17/02/06
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("快去准备做法材料~");
        
        CountDownLatch cdl = new CountDownLatch(3);
        new Thread(new BuyRice(cdl)).start();
        new Thread(new BuyVig(cdl)).start();
        new Thread(new BuyGuo(cdl)).start();
        cdl.await();
        System.out.println("材料买齐了，开始做饭");
    }
}

class BuyRice implements Runnable{
    private CountDownLatch cdl;
    public BuyRice(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("我去买米 -- 开始");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我去买米 -- 结束");
        cdl.countDown();
    }
}
class BuyVig implements Runnable{
    private CountDownLatch cdl;
    public BuyVig(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("我去买菜 -- 开始");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我去买菜 -- 结束");
        cdl.countDown();
    }
}
class BuyGuo implements Runnable{
    private CountDownLatch cdl;
    public BuyGuo(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("我去买锅 -- 开始");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我去买锅 -- 结束");
        cdl.countDown();
    }
}
