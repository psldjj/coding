package com.psl.coding;

import java.util.concurrent.locks.LockSupport;

public class ThreadParkTest {
    /**
     * 调用unpark后，线程会有一次直接跳过park的机会，
     * 1.多次调用unpark，也只会有一次跳过park的机会。
     * 2.线程没有start之前，unpark没有任何作用。
     * */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            System.out.println("123");
//            LockSupport.park();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("456");
            LockSupport.park();
            System.out.println("abc");
        });
        thread.start();
//        Thread.sleep(1000);
//        LockSupport.unpark(thread);
        LockSupport.unpark(thread);
//        thread.start();




    }
}
