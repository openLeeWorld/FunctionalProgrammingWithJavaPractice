package com.example.ch4;

import java.util.ArrayList;
import java.util.List;

public class final_Example {
    public static void main(String[] args) {
        final List<String> fruits = new ArrayList<>();
        // final은 실제로 참조하는 ArrayList가 아니라 참조 fruits에만 영향을 미칩니다.
        System.out.println(fruits.isEmpty());
        // 결과: true

        fruits.add("Apple");
        // ArrayList자체는 불변성의 개념이 없으므로 추가 가능

        System.out.println(fruits.isEmpty());
        // 결과: false

        //fruits = List.of("Mango", "Melon");
        // final 참조를 재할당 하는 것은 금지(컴파일 실패)

    }
}
