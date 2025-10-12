package com.psl.coding;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
    public static void main(String[] args) {


//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//        forkJoinPool.execute();
//        TestRecursiveTask a = new TestRecursiveTask(1);
//        TestRecursiveTask b = new TestRecursiveTask(2);
//        a.fork();
//        b.fork();
//        a.join();
//        b.join();
    }

    static class TestRecursiveTask extends RecursiveTask<Integer> {


        private int a;
        public TestRecursiveTask(int a) {
            this.a = a;
        }

        @Override
        protected Integer compute() {
            System.out.println(Thread.currentThread().getName());
            return a;
        }
    }

    static class TestRecursiveAction extends RecursiveAction{
        @Override
        protected void compute() {}
    }
}
