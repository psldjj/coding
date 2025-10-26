package com.psl.coding;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：
 * 读读不互斥
 * 读写、写写互斥
 * */
public class ReentrantReadWriteLockTest {
    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();

        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

        readLock.lock();

    }
}
