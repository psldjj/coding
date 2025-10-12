package com.psl.coding;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    public static void main(String[] args) throws InterruptedException {

        TestQueue testQueue = new TestQueue();
        new Thread(()->{
            for(int i=0;i<10;i++) {
                Object take = null;
                try {
                    take = testQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(take);
            }
        }).start();

        new Thread(()->{
            for(int i=0;i<10;i++) {
                try {
                    testQueue.put(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


        Thread.sleep(2000);

    }

    public static class TestQueue {
        private Object source;
        private final ReentrantLock lock = new ReentrantLock();
        //阻塞消费线程
        private final Condition notEmpty = lock.newCondition();
        //阻塞生产线程
        private final Condition notFull = lock.newCondition();
        public TestQueue(){

        }

        public Object take() throws InterruptedException {
            Object o;
            lock.lock();
            try{
                while(source == null){
                    notEmpty.await();
                }
                o = source;
                source = null;
                notFull.signal();
            }finally {
                lock.unlock();
            }
            return o;
        }

        public void put(Object o) throws InterruptedException {
            lock.lock();
            try {
                while(source != null){
                    notFull.await();
                }
                source = o;
                notEmpty.signal();
            }finally {
                lock.unlock();
            }
        }
    }


}
