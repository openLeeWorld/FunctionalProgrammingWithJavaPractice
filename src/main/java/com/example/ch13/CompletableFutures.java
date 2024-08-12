package com.example.ch13;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

public class CompletableFutures {
    private final static Predicate<CompletableFuture<?>> EXCEPTIONALLY =
            Predicate.not(CompletableFuture::isCompletedExceptionally);
    // 성공적인 완료를 확인하기 위한 Predicate는 특정 CompletableFuture 인스턴스에 국한되지 않음, 재사용 가능

    @SafeVarargs // 타입 안전성이 가변 길이 인자에서 없어지므로 경고를 없앰
    private static <T> Function<Void, List<T>> gatherResultsFn(CompletableFuture<T>... cfs) {
        // 공통 부분 리팩토링
        return unused ->
                Arrays.stream(cfs)
                .filter(Predicate.not(EXCEPTIONALLY))
                // 예외적 완료로 끝난 CompletableFuture 제외
                .map(CompletableFuture::join)
                // get과 같이 현재 스레드를 파이프라인이 완료될 때까지 차단
                .toList(); // 리스트로 변형
    }

    @SafeVarargs
    public static <T> CompletableFuture<List<T>> eachOf(CompletableFuture<T>... cfs) {
        // ...는 **varargs(variable-length argument)로 메서드가 가변 개수(0개 이상)의 인자를 받을 수 있도록 함
        // cfs는 CompletableFuture<T> 타입의 배열로 취급됨

        return CompletableFuture.allOf(cfs)
                .thenApply(gatherResultsFn(cfs)); // 각각의 반환 타입을 맞춤
        // 기존의 allOf를 호출하고 결과 집계 파이프라인과 결합
    }

    // 성공적으로 완료되고 오직 성공적인 결과만 반환하는 eachOf를 기반으로 한 bestEffort 헬퍼 메서드
    @SafeVarargs
    public static <T> CompletableFuture<List<T>> bestEffort(CompletableFuture<T>... cfs) {
        // ...는 **varargs(variable-length argument)로 메서드가 가변 개수(0개 이상)의 인자를 받을 수 있도록 함
        // cfs는 CompletableFuture<T> 타입의 배열로 취급됨

        return CompletableFuture.allOf(cfs)
                .exceptionally(ex -> null) 
                // 예외 발생 대신 CompletableFuture를 복구하기 위해 Function<Throwable, Void>를 필요로 함
                .thenApply(gatherResultsFn(cfs)); // 각각의 반환 타입을 맞춤
        // 기존의 allOf를 호출하고 결과 집계 파이프라인과 결합
    }
    
    private CompletableFutures () {
        // 기본 생성자 생략
    }
}
// 각각의 작업은 독립적으로 존재하며 자체적으로 사용됨
// 이러한 작업들을 결합함으로써 더 작은 부분들로 구성된 복잡한 해결책 생성 가능
