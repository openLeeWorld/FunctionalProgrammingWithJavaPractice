package com.example.ch13;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Future 타입은 비동기 계산을 위한 논블로킹 컨테이너
public class Future_Example {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var executor = Executors.newFixedThreadPool(10);
        // Callable<T>나 Runnable을 사용하기 위한 ExecutorService

        Callable<Integer> expensiveTask = () -> {
            System.out.println("(task) start");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("(task) done");
            return 42;
        };

        System.out.println("(main) before submitting the task");

        var future = executor.submit(expensiveTask);
        // 제출하는 순간 계산이 즉시 시작되어 결과값에 반영됨

        System.out.println("(main) after submitting the task");

        System.out.println("(main) before the blocking call future.get()");

        var theAnswer = future.get();
        // 계산이 완료 될 때까지(5초) 현재 스레드가 차단됨
        System.out.println("theAnswer: " + theAnswer);

        System.out.println("(main) after the blocking call future.get()");

        // Executor를 종료하여 JVM이 종료되도록 함
        executor.shutdown();
        // shutdown: 이미 제출한 태스크를 모두 수행한 후 스레드 풀 종료
        // shutdownNow: 현재 수행중인 태스크를 중지시키고, 대기 중인 태스크를 취소한 후 스레드 풀 종료
    }
}
