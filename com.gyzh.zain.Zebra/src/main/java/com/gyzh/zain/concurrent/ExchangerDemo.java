package com.gyzh.zain.concurrent;

import java.util.concurrent.Exchanger;

/**
 * 交换机
 * @author zain
 * 17/02/06
 */
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exc = new Exchanger<>();
        new Thread(new E1(exc)).start();
        new Thread(new E2(exc)).start();
    }
}

class E1 implements Runnable {
    private Exchanger<String> exc;
    public E1(Exchanger<String> exc) {
        this.exc = exc;
    }
    @Override
    public void run() {
        try {
            String msg = exc.exchange("天王盖地虎");
            Thread.sleep(1000);
            System.out.println("e1 get " + msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}

class E2 implements Runnable {
    private Exchanger<String> exc;
    public E2(Exchanger<String> exc) {
        this.exc = exc;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            String msg = exc.exchange("小鸡炖蘑菇");
            System.out.println("e2 get " + msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
