package com.example.ch13;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CompletableFuture_Example {
    public static void main(String[] args) {
        // Future<T>
        var executorService = ForkJoinPool.commonPool();
        Future<?> futureRunnable =
                executorService.submit(()->System.out.println("not returning a value"));
        Future<String> futureCallable =
                executorService.submit(() -> "Hello, Async World!");

        // CompletableFuture<T>
        CompletableFuture<Void> completableFutureRunnable =
                CompletableFuture.runAsync(() -> System.out.println("not returning a value"));

        CompletableFuture<String> completableFutureSupplier =
                CompletableFuture.supplyAsync(() -> "Hello, Async World!");
        // CompletionStage 인스턴스들의 선언적이고 함수적인 파이프라인 구축 출발점
        
        // 중첩된 단계 풀기
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 42);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 23);

        BiFunction<Integer, Integer, CompletableFuture<Integer>> task =
                (lhs, rhs) -> CompletableFuture.supplyAsync(() -> lhs + rhs);

        CompletableFuture<Integer> combined = future1.thenCombine(future2, task) 
                // task의 변환값은 thenCombine에 의해 래핑되어 CompletionStage<CompletionStrage<Integer>>를 생성
                .thenCompose(Function.identity()); // 중첩된 단계를 풀어내어 CompletionStage<Integer>가 됨
        
        // Either 연산과 거부된 단계 (acceptEither는 이전 단계가 거부된 경우 다른 단계가 정상적으로 완료되도라도 거부된 상태로 유지)
        CompletableFuture<String> failed = 
                CompletableFuture.supplyAsync(() -> { throw new RuntimeException(); });
        var rejected = failed.acceptEither(failed, System.out::println);
        // 이전 단계가 실패했기 때문에 출력 없음

        CompletableFuture<String> notFailed =
                CompletableFuture.supplyAsync(() ->  "Success!" );
        var resolved = notFailed.acceptEither(notFailed, System.out::println);
        // 이전 단계가 성공했기 때문에 출력 있음
    }
}
