package com.psl.coding;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ThreadTest {
    private static volatile  int t = 1;
    public static void main(String[] args) throws InterruptedException {



        ArrayBlockingQueue<Integer> list = new ArrayBlockingQueue<>(10);

        list.add(1);
        list.add(2);
        list.add(3);


        Thread thread = new Thread(()->{
            while(t>0){

            }
            Integer a = null;
            while(t<=0){
                try {
                    a = list.take();
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
                System.out.println(Thread.currentThread().isInterrupted());
                System.out.println(a);
            }
        });
        thread.start();

        thread.interrupt();

        t--;
        Thread.sleep(1000);
        t++;
    }
}
