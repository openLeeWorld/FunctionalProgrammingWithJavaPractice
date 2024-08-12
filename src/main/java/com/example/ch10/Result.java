package com.example.ch10;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

// 레코드 컴포넌트들은 각기 다른 상태를 나타냄
public record Result<V, E extends Throwable>(V value,
                                             E throwable,
                                             boolean isSuccess) {
    // 편의 팩토리 메서드는 직관적인 api 제공
    public static <V, E extends Throwable> Result<V, E> success(V value) {
        return new Result<>(value, null, true);
    }

    public static <V, E extends Throwable> Result<V, E> failure(E throwable) {
        return new Result<>(null, throwable, false);
    }

    // 변환기 추가 (성공, 실패 케이스에 대해 Function 적용)
   public <R> Optional<R> mapSuccess(Function<V, R> fn) {
        return this.isSuccess ? Optional.ofNullable(this.value).map(fn)
                : Optional.empty();
   }
   // 실패 케이스에 대해 함수 적용
    public <R> Optional<R> mapFailure(Function<E, R> fn) {
        return this.isSuccess ? Optional.empty()
                : Optional.ofNullable(this.throwable).map(fn);
    }
    // 각각 함수 적용
    public <R> R map(Function<V, R> successFn, Function<E, R> failureFn) {
        return this.isSuccess ? successFn.apply(this.value)
                : failureFn.apply(this.throwable);
    }

    // 특정 상태에 대응하기 위해 메서드 추가
    // 성공 시 행동
    public void ifSuccess(Consumer<? super V> action) {
        if (this.isSuccess) {
            action.accept(this.value);
        }
    }
    // 실패 시 행동
    public void ifFailure(Consumer<? super E> action) {
        if (!this.isSuccess) {
            action.accept(this.throwable);
        }
    }
    // 성공, 실패시 둘 다 다룸
    public void handle(Consumer<? super V> successAction,
                       Consumer<? super E> failureAction) {
        if (this.isSuccess) {
            successAction.accept(this.value);
        } else {
            failureAction.accept(this.throwable);
        }
    }
    
    // 대체값을 제공하기 위한 편의 메서드 제공
    public V orElse(V other) {
        return this.isSuccess ? this.value : other;
    }

    public V orElseGet(Supplier<? extends V> otherSupplier) {
        return this.isSuccess ? this.value : otherSupplier.get();
    }
    /*
    public V orElseThrow() {
        if(!this.isSuccess) {
            sneakyThrow(this.throwable);
            return null;
        }
        return this.value;
    }
    // 컴파일러 몰래 던지기 메서드
    private <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }
     */
}
