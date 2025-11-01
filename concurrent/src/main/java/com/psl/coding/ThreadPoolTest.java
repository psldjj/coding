package com.psl.coding;

import java.util.concurrent.*;

public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
//        executeTest();


        newPoolTest();

    }


    public static class MyQueue<T> extends ArrayBlockingQueue<T> {

        public MyQueue(int capacity) {
            super(capacity);
        }

        @Override
        public boolean offer(T runnable) {
            return false;
        }

        public boolean doOffer(T runnable){
            return super.offer(runnable);
        }
    }

    /**
     * 尝试修改执行流程
     * 优先新增到最大线程数、然后放到队列，最后才拒绝
     * */
    public static void newPoolTest() throws InterruptedException {
        MyQueue<Runnable> queue = new MyQueue<>(2);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 2, 10, TimeUnit.SECONDS,queue, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if(!queue.doOffer(r)){
                    System.out.println("fsdfdsfsd");
                }
            }
        });


        threadPoolExecutor.execute(()->{
            System.out.println(1);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        threadPoolExecutor.execute(()->{
            System.out.println(2);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        threadPoolExecutor.execute(()->{
            System.out.println(3);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });


        threadPoolExecutor.execute(()->{
            System.out.println(4);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        Thread.sleep(10000);

        threadPoolExecutor.execute(()->{
            System.out.println(5);
        });

    }

    /**
     * 任务提交流程
     * */
    public static void executeTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(()->{
            System.out.println("abc");
        });

        test:
        for(;;){
            for(;;){
                System.out.println(233);
                break test;
            }
        }
        System.out.println(111);

    }
}
