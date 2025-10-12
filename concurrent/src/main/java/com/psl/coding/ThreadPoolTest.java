package com.psl.coding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    public static void main(String[] args) {
        executeTest();
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
