package com.psl.coding;


import jdk.internal.misc.Unsafe;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;

public class AqsTest {

    public static void main(String[] args) throws Exception {
//        ReentrantLock reentrantLock = new ReentrantLock();
//        reentrantLock.lock();
//
//
//
//
//        Thread thread = new Thread(() -> {
//            reentrantLock.lock();
//        },"abc");
////        System.out.println(thread.getName());
//        thread.start();
//
////        Thread.sleep(1000);
//        reentrantLock.unlock();

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LockSupport.park();
            System.out.println("abcc");
        });
        thread.start();
        Thread.sleep(500);
        LockSupport.unpark(thread);



//
//        Thread.sleep(1000);
//
//        thread.interrupt();
//
//        Thread.sleep(1000);


    }
    //公平锁
    public static class FairSyncTest extends AbstractQueuedSynchronizer{

        final void lock(){
            if(!initialTryLock()){
                acquire(1);
            }
        }

        final boolean initialTryLock(){
            Thread currentThread = Thread.currentThread();
            int c = getState();
            //为0表示没有人持有锁
            if(c == 0){
                //没有等待的线程，并且cas成功
                if(!hasQueuedThreads() && compareAndSetState(0,1)){
                    setExclusiveOwnerThread(currentThread);
                    return true;
                }
            }else if(getExclusiveOwnerThread() == currentThread){
                //当前线程持有锁，所以这里不会有并发，直接++c没问题
                setState(++c);
                return true;
            }
            return false;
        }


        @Override
        protected boolean tryAcquire(int arg) {
            if(getState() == 0 && !hasQueuedPredecessors() &&
            compareAndSetState(0,arg)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
    }

    public static class LockTest{
        private static final Unsafe UNSAFE = Unsafe.getUnsafe();
        private volatile int state;
        private static final long STATE = UNSAFE.objectFieldOffset(LockTest.class,"state");
        private static final long HEAD = UNSAFE.objectFieldOffset(LockTest.class,"head");

        private volatile Node head;
        private volatile Node tail;

        private Thread exclusiveOwnerThread;


        public boolean compareAndSet(int expect, int update) {
            return UNSAFE.compareAndSetInt(this,STATE,expect,update);
        }


        public void acquire(int arg){

        }

        //初始化虚拟节点其实就做两件事
        //新建一个节点
        //head和tail指向这个节点
        public Node initQueue(){
            //tail不为空说明初始化完成
            while(tail == null){
                //设置head完成，但是设置tail还没完成，就可以让出cpu，让其他线程完成初始化
                //是一个优化点
                //假设 cas head的线程成功了，但是成功后一直没被调度，为了防止其他的线程
                //创建大量的dummy，所以加了这一个判断
                if(head != null){
                    Thread.onSpinWait();
                }else{
                    Node dummy;
                    try {
                        dummy = new Node();
                        //依然可能有大量的线程走到这一步，创建大量dummy对象导致oom
                    }catch (OutOfMemoryError e){
                        return null;
                    }
                    if(UNSAFE.compareAndSetReference(this,HEAD,null,dummy)){
                        tail = dummy;
                        return tail;
                    }
                }
            }
            return tail;
        }
    }

    public static class Node{

        public Node() {
        }

        Thread thread;
        volatile Node pre;
        volatile Node next;

    }
}
