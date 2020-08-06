package com.example.demo;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        test2();
    }

    private static void test2() throws InterruptedException {
        Mono<String> mono = Mono.fromCallable(() -> {
            System.out.println(Thread.currentThread().getName() + " mock http response");
            return "Http response as string";
        });
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getName() +" main thread 1111");
        Thread.sleep(1000L);
        mono.subscribe(m-> System.out.println("result: "  +m.toUpperCase() ));
    }

    private static void test1() throws InterruptedException, ExecutionException {
        CompletableFuture<String> mono = Mono.fromCallable(() -> {

            System.out.println(Thread.currentThread().getName() + " mock http response");

            return "Http response as string";

        }).toFuture();
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getName() +" main thread 1111");
        Thread.sleep(1000L);
        System.out.println(mono.get());
    }
}
