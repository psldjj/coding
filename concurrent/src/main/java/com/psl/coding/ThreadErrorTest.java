package com.psl.coding;

/**
 * 线程异常
 * 1.线程抛出异常后会到terminated 终止态
 * 2.在未捕获异常处理器中，线程依然是运行态。实际就是当前线程在运行这个捕获逻辑
 * 3.没被捕获的异常，最终就是System.err打印日志，然后线程终止
 * */
public class ThreadErrorTest {
    public static void main(String[] args) throws InterruptedException {

        ThreadGroup mygroup = new ThreadGroup("mygroup");


        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("默认异常处理器");
            }
        });


        Thread thread = new Thread(()->{
            System.out.println("运行");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            int a = 1/0;


        });

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("线程自己的异常处理器");
            }
        });

        thread.start();
        //等待完成
        thread.join();


        System.out.println(thread.getState());




    }
}
