package com.example.ch11;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

// Thunk는 연산을 감싸는 래퍼로, 결과가 필요할 때까지 연산을 지연하기 위해 사용
public class Thunk<T> implements Supplier<T> {
    private final Supplier<T> expression;
    // 실제 Supplier<T>는 평가를 지연시키기 위해 저장되어야 함
    private T result;
    // 평가 후 결과 저장 용도
    private Thunk(Supplier<T> expression) {
        this.expression = expression;
    }

    @Override
    public T get() {
        if (this.result == null) {
            this.result = this.expression.get();
        }
        return this.result;
     }

     public static <T> Thunk<T> of(Supplier<T> expression) {
        if (expression instanceof Thunk<T>) {
            return (Thunk<T>) expression;
        }
        return new Thunk<T>(expression);
     } // thunk를 만들기 위한 정적 팩토리 메서드

     // 함수의 합성을 지원하기 위해 glue method 추가
    public static <T> Thunk<T> of(T value) {
        return new Thunk<T> (() -> value);
    }

    public <R> Thunk<R> map(Function<T, R> mapper) {
        return Thunk.of(() -> mapper.apply(get()));
    }

    public void accept(Consumer<T> consumer) {
        consumer.accept(get());
    }
    // supplier는 단 한번만 평가됨
}
