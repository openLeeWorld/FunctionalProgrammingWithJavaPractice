package com.example.ch10;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R> extends Function<T, R> {
    R applyThrows(T elem) throws Exception;
    // 단일 추상 메서드는 원래의 메서드와 유사하지만 Exception 발생시킴

    @Override
    default R apply(T t) {
        try {
            return applyThrows(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    } // 기존의 SAM은 어떤 Exception이든 언체크 RuntimeException으로 
    // 래핑하기 위해 기본 메서드로 구현됨

    public static <T, R> Function<T, R> uncheck(ThrowingFunction<T, R> fn) {
        return fn::apply;
    } // catch-or-specify 요구 사항을 우회하기 위해 Function<T, R>에서 발생하는 
    // 어떤 예외도 검사하지 않도록 도와주는 static 헬퍼 메서드
}
