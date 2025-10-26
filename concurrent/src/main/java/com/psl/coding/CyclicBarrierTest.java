package com.psl.coding;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        Thread thread = new Thread(() -> {
            try {
                cyclicBarrier.await();

                System.out.println("abc");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        });

        thread.start();

        Thread.sleep(5000);
        System.out.println("aaa");
        cyclicBarrier.await();

        System.out.println("bbb");
    }
}
