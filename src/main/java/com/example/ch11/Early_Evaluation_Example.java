package com.example.ch11;

import java.util.function.IntSupplier;

public class Early_Evaluation_Example {
    public static void main(String[] args) {
        // var result = add_strict(5, (1/0));
        // 위 식은 1/0이 인수로 넘겨질 때 평가되서 예외 발생

        var result = add_lazy(() -> 5, () -> 1/0);
        // 람다의 실제 바디는 getAsInt로 명시적으로 호출되기 전까지는 계산되지 않음
    }

    private static int add_strict(int x, int y) {
        return x + x;
    }

    private static int add_lazy(IntSupplier x, IntSupplier y) {
        var actualX = x.getAsInt();
        return actualX + actualX;
    }
}

// cf) 자바에서 메서드 인수들은 값으로 전달되지만 원시 타입이 아니면
// references라는 타입으로 객체 핸들 전달
// strictness는 일을 하는 것에 중점을 둠 ex) 변수 선언, 값 할당, 인수 등
// laziness 는 할 일을 고려하는 것에 중점을 둠 (ex) 람다