package com.psl.coding;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 三个线程循环打印ABC 10次
 * 1.循环处理
 * 2.精准唤醒
 * */
public class PrintAbcTest {

    private static final Map<String,Thread> threadMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        printAbc2();

    }

    /**
     * 精准控制
     * */
    public static void printAbc2(){

        Thread threadA = new Thread(new PrintTask("A","B"));
        Thread threadB = new Thread(new PrintTask("B","C"));
        Thread threadC = new Thread(new PrintTask("C","A"));

//        threadA.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                System.out.println(e.getMessage());
//
//            }
//        });

        threadMap.put("A",threadA);
        threadMap.put("B",threadB);
        threadMap.put("C",threadC);
        threadA.start();
        threadB.start();
        threadC.start();

        // 启动第一个线程
        LockSupport.unpark(threadA);
    }

    static class PrintTask implements Runnable {
        private String content;
        private String nextThread;

        public PrintTask(String content, String nextThread) {
            this.content = content;
            this.nextThread = nextThread;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                LockSupport.park();
                System.out.print(content);
                Thread thread = threadMap.get(nextThread);
                LockSupport.unpark(thread);
            }
        }
    }

    /**
     * 非精准控制
     * */
    public static void  printAbc1(){

        Object obj = new Object();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Thread a = new Thread(() -> {
            for(int i =0;i<10;i++) {
                synchronized (obj) {
                    while(atomicInteger.get()%3 != 0) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print("A");
                    atomicInteger.getAndAdd(1);
                    obj.notifyAll();
                }
            }
        });

        Thread b = new Thread(() -> {
            for(int i =0;i<10;i++) {
                synchronized (obj) {
                    while(atomicInteger.get()%3 != 1) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print("B");
                    atomicInteger.getAndAdd(1);
                    obj.notifyAll();
                }
            }
        });

        Thread c = new Thread(() -> {
            for(int i =0;i<10;i++) {
                synchronized (obj) {
                    while(atomicInteger.get()%3 != 2) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print("C");
                    atomicInteger.getAndAdd(1);
                    obj.notifyAll();
                }
            }
        });


        a.start();
        b.start();
        c.start();
    }
}
