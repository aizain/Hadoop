package com.gyzh.zain.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 执行器
 * @author zain
 * 17/02/06
 */
public class ExecutorServiceDemo {
    public static void main(String[] args) throws InterruptedException {
        // Thread
        CountDownLatch cdl = new CountDownLatch(1000);
        long begin = System.currentTimeMillis();
        for (int i=0; i<1000; i++) {
            new Thread(new Ex1(cdl)).start();
        }
        cdl.await();
        long end = System.currentTimeMillis();
        System.out.println("普通线程使用时间： " + (end - begin) + "ms");
        
        // ThreadPoolExecutor
        ExecutorService es = new ThreadPoolExecutor(
                500, // corePoolSize, // 核心线程数量
                800, // maximumPoolSize, // 最大线程数量
                5, // keepAliveTime, // 多余线程的超时时间
                TimeUnit.SECONDS, // unit, // 超时时间单位
                new ArrayBlockingQueue<Runnable>(300), // workQueue, // 超过核心数量后，先在该队列中排队，队列排满了，再创建线程，新创建的线程执行新的任务，不管队列中的，不超过最大线程
                new RejectedExecutionHandler() { // rejectHandler // 拒绝方法，超过最大线程数量后，调用该对象的方法
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        executor.remove(r);
                        System.out.println("任务被拒绝了");
                    }
                }
        );
        testThread(es, "线程池");
        
        // newCachedThreadPool
        ExecutorService es2 = Executors.newCachedThreadPool();
        testThread(es2, "线程池newCachedThreadPool");
        
        // newFixedThreadPool
        ExecutorService es3 = Executors.newFixedThreadPool(300);
        testThread(es3, "线程池newFixedThreadPool");
        
        // newScheduledThreadPool
        ExecutorService es4 = Executors.newScheduledThreadPool(300);
        testThread(es4, "线程池newScheduledThreadPool");
        
        // newSingleThreadExecutor
        ExecutorService es5 = Executors.newSingleThreadExecutor();
        testThread(es5, "线程池newSingleThreadExecutor");
    }
    
    public static void testThread(ExecutorService es, String msg) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(1000);
        long begin = System.currentTimeMillis();
        for (int i=0; i<1000; i++) {
            es.execute(new Ex1(cdl));
        }
        cdl.await();
        es.shutdown();
        long end = System.currentTimeMillis();
        System.out.println(msg + "使用时间： " + (end - begin) + "ms");
    }
}


class Ex1 implements Runnable {
    private CountDownLatch cdl;
    public Ex1(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cdl.countDown();
    }
}
