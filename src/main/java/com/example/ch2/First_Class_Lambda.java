package com.example.ch2;

import java.util.function.UnaryOperator;

public class First_Class_Lambda {
    public static void main(String[] args) {
        // 변수 할당로 할당 가능한 람다
        UnaryOperator<Integer> quadraticFn = x -> x * x;
        System.out.println(quadraticFn.apply(5)); // 25

        // 람다식을 변수에 할당해서 apply메서드로 사용가능

        UnaryOperator<Integer> multiplyWithFive = multiplyWith(5);
        System.out.println(multiplyWithFive.apply(6)); // 30
    }

    // 메서드 인수로 사용 가능한 람다
    public Integer apply(Integer input, UnaryOperator<Integer> operation) {
        return operation.apply(input);
    }

    // 반환값으로 사용가능한 람다
    public static UnaryOperator<Integer> multiplyWith(Integer multiplier) {
        return x -> multiplier * x;
    }
}
