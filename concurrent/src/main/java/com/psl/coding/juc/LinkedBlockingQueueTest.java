package com.psl.coding.juc;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 1.链表实现，支持入队和出队并发执行
 * 2.两把锁，读取一把锁，写入一把锁
 * 3.两个condition。入队唤醒出队，出队唤醒入队
 * 4.有虚拟头节点，new的时候就初始化一个虚拟头结点，头尾指针都指向这个虚拟节点
 *
 * （1）入队操作：
 * ①获取put锁，创建新的node，将last.next指向node，last指向node
 * 唤醒出队线程（此时是需要获取take锁的）
 *
 * (2) 出队操作：
 * ①获取出队锁，将head.next节点（first)的元素暂存，
 * ②将first的元素设置为空，将head指向first。
 * ③返回暂存的元素
 * 如果队列刚刚为满（现在取出了一个元素，所以不为满了，则唤醒入队线程（此时需要获取put锁））
 *
 * 出队操作链表和入队操作链表是完全可以并发的。
 * 因为入队操作的是节点的next
 * 出队操作的是节点的item，并没有临界资源。
 *
 * */
public class LinkedBlockingQueueTest {
    public static void main(String[] args) {
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<String>();
    }



}
