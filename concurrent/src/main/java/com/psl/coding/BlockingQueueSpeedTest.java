package com.psl.coding;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试下ArrayBlockingQueue
 * 和LinkedBlockingQueue的速度
 * */
public class BlockingQueueSpeedTest {
    public static void main(String[] args) throws InterruptedException {

//    arrayTest();

    linkedTest();

    }
    public static void arrayTest() throws InterruptedException {
        int total = 1000000;

        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(total);


        AtomicInteger getCount = new AtomicInteger(0);

        AtomicInteger putCount = new AtomicInteger(0);

        ExecutorService consumer = Executors.newFixedThreadPool(4);

        ExecutorService produce = Executors.newFixedThreadPool(4);


        long start = System.currentTimeMillis();
        consumer.execute(()->{
            while(true) {
                Integer poll = arrayBlockingQueue.poll();
                int andAdd = getCount.getAndAdd(1);
                if (andAdd >= total) {
                    long end = System.currentTimeMillis();
                    System.out.println(end-start);
                    break;
                }
            }
        });

        produce.execute(()->{
            try {
                while(putCount.getAndAdd(1) < total-1) {
                    arrayBlockingQueue.put(1);
                }
            } catch (InterruptedException e) {

            }
        });



        Thread.sleep(2000);
    }

    public static void linkedTest() throws InterruptedException {
        int total = 1000000;



        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(total);

        AtomicInteger getCount = new AtomicInteger(0);

        AtomicInteger putCount = new AtomicInteger(0);

        ExecutorService consumer = Executors.newFixedThreadPool(4);

        ExecutorService produce = Executors.newFixedThreadPool(4);


        long start = System.currentTimeMillis();
        consumer.execute(()->{
            while(true) {
                Integer poll = linkedBlockingQueue.poll();
                int andAdd = getCount.getAndAdd(1);
                if (andAdd >= total) {
                    long end = System.currentTimeMillis();
                    System.out.println(end-start);
                    break;
                }
            }
        });

        produce.execute(()->{
            try {
                while(putCount.getAndAdd(1) < total-1) {
                    linkedBlockingQueue.put(1);
                }
            } catch (InterruptedException e) {

            }
        });



        Thread.sleep(2000);
    }
}
