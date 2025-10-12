package com.psl.coding;


/**
 * 1.wait的特性：
 * wait会释放锁
 * 2.notify和notifyAll的区别
 * notify唤醒某个线程，notifyAll唤醒所有线程
 * */
public class WaitNotifyTest {
    /**
     * 1.wait的特性：
     * wait会释放锁
     * 2.notify和notifyAll的区别
     * notify唤醒某个线程，notifyAll唤醒所有线程
     * */
    public static void main(String[] args) {
       Object obj = new Object();

       synchronized (obj){
           //唤醒下所有等待的线程
           obj.notifyAll();
           //唤醒某个等待的线程
           obj.notify();
       }
    }
}
