package com.gyzh.zain.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 * @author zain
 * 17/02/06
 */
public class BlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
        queue.put("a");
        queue.put("b");
        queue.put("c");
        // queue.put("a");
        // queue.add("a");
        // System.out.println(queue.offer("a"));
        // System.out.println(queue.offer("a", 3, TimeUnit.SECONDS));
        
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        // System.out.println(queue.take());
        // System.out.println(queue.remove());
        // System.out.println(queue.poll());
        System.out.println(queue.poll(3, TimeUnit.SECONDS));
    }
}
