package com.psl.coding.juc;

import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 *
 * add 注意队列满会报异常
 * offer 队列满返回false，也有阻塞的方法
 * put 阻塞的，
 * ArrayBlockingQueue，使用数组实现，具有容量限制
 * 一个数组，
 * 一把锁，
 * 两个condition
 * 1.定义一个数组
 * 2.入队列时获取锁
 * */
public class ArrayBlockingQueueTest {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
    }
}
