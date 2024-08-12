package com.example.ch6;

import java.util.stream.Stream;

public class Stream_Cost_Example {
    public static void main(String[] args) {
        Stream.of("ananas", "oranges", "apple", "pear", "banana")
                .filter(s -> s.startsWith("a")) // filter 5번 호출
                .map(String::toUpperCase) // map 2번
                .sorted() // 한 번
                .forEach(System.out::println); // 2번 호출
        // 필터를 먼저 호출함으로써 비용 절감 가능

        // short-circuit 연산 (stream 조기 중단)
        var result = Stream.of("apple", "orange", "banana", "melon")
                //.filter(str -> str.contains("e")) // 주석 해제 시 아래도 시동됨
                .peek(str -> System.out.println("peek 1: " + str))
                .map(str -> {
                    System.out.println("map: " + str);
                    return str.toUpperCase();
                })
                .peek(str -> System.out.println("peek 2: " + str))
                .count();
        System.out.print("Count: " + result);
    }
}
