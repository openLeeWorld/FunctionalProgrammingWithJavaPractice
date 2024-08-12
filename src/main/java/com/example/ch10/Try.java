package com.example.ch10;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

// try-success-failure pattern 모방
// 람다와 핸들러를 수용하기 위한 필수적인 구조
public class Try <T, R> implements Function<T, Optional<R>>{
    private final Function<T, R> fn;
    private final Function<RuntimeException, R> failureFn;

    public static <T,R> Try<T,R> of(ThrowingFunction<T,R> fn) {
        Objects.requireNonNull(fn);
        return new Try<>(fn, null);
    } // 정적 팩토리 메서드는 다른 함수형 파이프라인과 유사한 인터페이스 제공

    public Try(Function<T,R> fn, Function<RuntimeException, R> failureFn) {
        this.fn = fn;
        this.failureFn = failureFn;
    }

    // 성공 처리하기
    public Try<T,R> success(Function<R, R> successFn) {
        Objects.requireNonNull(successFn);

        var composedFn = this.fn.andThen(successFn);
        return new Try<>(composedFn, this.failureFn);
    } // 변경 불가능하게 설계되어서 Try의 새 인스턴스를 반환
    
    // 실패 처리하기
    public Try<T, R> failure(Function<RuntimeException, R> failureFn) {
        Objects.requireNonNull(failureFn);
        return new Try<>(this.fn, failureFn);
    }

    // apply 메서드를 통해 값을 파이프라인으로 전달하고 핸들러가 작업 수행
    @Override
    public Optional<R> apply(T value) {
        try {
            var result = this.fn.apply(value);
            return Optional.ofNullable(result);
        } catch (RuntimeException e) {
            if (this.failureFn != null) {
                var result = this.failureFn.apply(e);
                return Optional.ofNullable(result);
            }
        }
        return Optional.empty();
    }
}
