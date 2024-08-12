package com.example.ch7;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Stream_Usage_Example {
    public static void main(String[] args) {
        // 1. primitive stream
        Stream<Long> longStream = Stream.of(5L, 23L, 42L);
        // Autoboxed version
        LongStream longStream1 = LongStream.of(1,2,3);
        // primitive Version

        // 2. iterative stream
        // Loop와 동등한 스트림 iterate
        IntStream.iterate(1, // seed, 초기값
                idx -> idx < 5, // 종료 조건
                idx -> idx + 1) // 반복값 증가, 스트림 반환 값 필요
                .forEachOrdered(System.out::println);
        System.out.println();

        // 3. infinite stream
        Stream<UUID> uuid = createStream(1);
        System.out.println(uuid.toList());
        System.out.println();

        // 병렬 환경에서 limit 연산을 사용하면 처음 n개 보장이 안됨?
        Stream.generate(new AtomicInteger()::incrementAndGet)
                        .parallel() .limit(1_000L)
                        .mapToInt(Integer::valueOf)
                        .max()
                        .ifPresent(System.out::println);
        // 동시성 때문에 1000이 아니라 그 이상에서 멈춰버림
        System.out.println();

        // 4. 스트링에서 배열 변환
        String[] fruits = new String[] { "Banana", "Melon", "Orange" };
        String[] result = Arrays.stream(fruits)
                .filter(fruit -> fruit.contains("a"))
                .toArray(String[]::new);
        System.out.println(Arrays.toString(result));
        System.out.println();

        // 원시 타입 배열
        int[] fibonacci = new int[]{0, 1, 1, 2, 3, 5, 8, 13, 21 ,34};

        int[] evenNumbers = Arrays.stream(fibonacci)
                .filter(value -> value % 2 == 0)
                .toArray();

        System.out.println(Arrays.toString(evenNumbers));
        System.out.println();
        
        // 그 외, 저수준 스트림 사용, 파일 I/O 스트림 등
    }

    // 3. infinite stream
    public static Stream<UUID> createStream(long count) {
        return Stream.generate(UUID::randomUUID)
                .limit(count);
    }
}
