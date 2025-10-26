package com.psl.coding.juc;


import java.util.concurrent.PriorityBlockingQueue;

/**
 * 优先级队列
 * 1.数组实现，无限大小
 * 2.一把锁，一个condition 用来唤醒消费者
 * */
public class PriorityBlockingQueueTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
    }
}
