package com.psl.coding.juc;


import java.util.concurrent.SynchronousQueue;

/**
 * 无长度队列（生产者直接交给消费者）
 *
 * */
public class SynchronousQueueTest {


    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        for(int i =0;i<3;i++){
            new Thread(()->{
                int t = 0;
                while(true){
                    try {
                        queue.put(t++);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }




        for(int i =0;i<3;i++){
            new Thread(()->{
                while(true){
                    try {
                        Integer take = queue.take();
                        System.out.println("消费"+take);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }).start();
        }
    }
}
