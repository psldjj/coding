package com.psl.coding;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> abc = CompletableFuture.runAsync(() -> {
            System.out.println("abc");
        }).thenRun(() -> {
            System.out.println("123");
        });

        CompletableFuture<Void> abc1 = CompletableFuture.runAsync(() -> {
            System.out.println("abc");
        }).thenAccept(System.out::println);


        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(e -> {
            System.out.println(e);
            return e + 1;
        }).thenAccept(System.out::println);

        CompletableFuture<String> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenCompose(e -> {
            System.out.println(e+"1");
            return CompletableFuture.supplyAsync(() -> {
                return e + "1";
            });
        });


        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            return 2;
        }), (a,b) -> {
            System.out.println(a+b);
            return a+b;
        });


        abc1.get();

    }
}
