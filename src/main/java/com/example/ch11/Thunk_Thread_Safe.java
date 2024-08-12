package com.example.ch11;

import java.util.function.Supplier;

public class Thunk_Thread_Safe<T> implements Supplier<T> {
    private static class Holder<T> implements Supplier<T> {
        // Supplier<T>를 상속함으로써, expression 필드에 대한 대체 가능
        private final T value; 
        // 평가된 값을 보유하는 value
        Holder(T value) {
            this.value = value;
        }

        @Override
        public T get() {
            return this.value;
        }
    }
    
    private Supplier<T> holder; // 표현식이 평가된 후에 값이 변경되므로 final 아님

    private Thunk_Thread_Safe(Supplier<T> expression) {
        this.holder = () -> evaluate(expression);
    } // 생성자에서 람다로 지연 평가

    // compare & swap :  변수의 값과 기대되는 값이 같다면 새로운 값으로 교체
    // 이 연산은 원자성을 충족해야 하므로 synchronized 필요
    private synchronized T evaluate(Supplier<T> expression) {
        if (!(this.holder instanceof Holder)) {
            var evaluated = expression.get();
            this.holder = new Holder<>(evaluated);
        } // holder 필드가 초기 표현식을 평가하기 위한 람다를 보유, Holder 인스턴스로 교체됨
        return this.holder.get();
    } // 다른 스레드에 의해 평가가 이미 되있다면 이미 캐시된 값을 리턴

    @Override
    public T get() {
        return this.holder.get();
    }
    // 개선된 Thunk는 표현식의 평가와 접근을 분리함으로써 경쟁 조건을 제거
    // 나머지 static 헬퍼나 추가 메서드는
    // 동일
}
