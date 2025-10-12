package com.psl.coding;

/**
 * 1.核心特性
 * 非公平锁
 * 可重入
 * 锁会有升级过程  无锁->偏向锁->轻量级锁->重量级锁  （jdk15默认关闭偏向锁）
 *
 * 2.底层原理
 * 对象头记录锁的状态信息（Mark Word 区域）
 * 无锁：00
 * 偏向锁： 升级到偏向锁时，CAS修改mark word到偏向锁状态和当前持有锁的线程id
 * 轻量级锁：在线程的栈帧创建lock record，cas的方式尝试将mark word修改成指向lock record的引用地址。
 *        细节：升级到轻量锁非常复杂，
 *        (1)假如处于偏向线程A状态，线程B尝试获取锁：
 *         需要等待全局安全点（stop the word）；
 *         判断A线程的状态，如果依然在运行中并且需要持有锁，那么直接优先让将mark word修改成A线程的lock record的引用。此时B线程拿到的mark word还是偏向锁状态，所以cas一定会失败。
 *         如果线程A在运行中并且不需要锁，那么直接偏向B。
 *         如果A没有在运行中，那么B直接通过CAS来尝试获取锁即可
 * 重量级锁：讲mark word执行monitor对象，
 *          线程阻塞到monitor的等待队列上。等待唤醒
 *
 * */
public class SynchronizedTest {
    public static void main(String[] args) {

    }
}
