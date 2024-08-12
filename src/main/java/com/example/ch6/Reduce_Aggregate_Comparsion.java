package com.example.ch6;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Reduce_Aggregate_Comparsion {
    public static void main(String[] args) {
        var numbers = List.of(1,2,3,4,5,6);
        int total = numbers.stream().reduce(0, Integer::sum);
        System.out.println(total);
        // 부분 결과 생성 비용이 낮은 경우에 불변 누적을 사용하는 reduce가 적합

        var strings = List.of("a","b","c","d","e");
        // stream reduce는 불변 축소로 연결할 때 단계마다 새로운 string을 생성
        var reduced = strings.stream().reduce("", String::concat);
        System.out.println(reduced);
        
        // stream 컬렉터 - 사용자 정의
        var joiner = strings.stream()
                .collect(Collector.of(() -> new StringJoiner(""), // supplier<A>
                        StringJoiner::add, // BiConsumer<A, T>
                        StringJoiner::merge, // BinaryOperator<A, T>
                        StringJoiner::toString)); // Function<A, R>
        System.out.println(joiner);
        
        // stream collector - 사전 정의
        var collectWithCollectors = strings.stream()
                .collect(Collectors.joining());
        // String.join("", strings);

        System.out.println(collectWithCollectors);
        // Collector는 가변 컨테이너를 활용하여 스트림의 요소를 보다 효과적으로 축소 가능
    }
}
