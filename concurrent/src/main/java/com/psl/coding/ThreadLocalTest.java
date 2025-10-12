package com.psl.coding;



public class ThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        Thread thread = new Thread(()->{
            threadLocal.set("abc");
            String s = threadLocal.get();
            System.out.println(s);
        });

        thread.start();

        Thread.sleep(1000);

        System.out.println(threadLocal.get());




    }
}
