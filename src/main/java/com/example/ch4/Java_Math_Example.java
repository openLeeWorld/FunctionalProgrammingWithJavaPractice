package com.example.ch4;

import java.math.BigDecimal;

public class Java_Math_Example {
    public static void main(String[] args) {
        // java.math 타입은 새로운 결과를 가진 새로운 객체를 반환
        // 더 넓은 범위와 높은 정밀도로 사이드 이펙트가 없는 계산
        var theAnswer = new BigDecimal(42);
        var result = theAnswer.add(BigDecimal.ONE);

        // 계산 결과
        System.out.println(result);
        // 출력: 43

        // 원본 값이 변경되지 않음
        System.out.println(theAnswer);
        // 출력: 42

        // 불변 수학 타입은 오버헤드 및 추가 메모리가 요구되지만
        // 높은 정밀도를 자랑함.
    }
}
