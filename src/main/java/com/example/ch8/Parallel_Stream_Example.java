package com.example.ch8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parallel_Stream_Example {
    public static void main(String[] args) throws IOException {
        var location = Paths.get("./src\\main\\java\\com\\example\\ch8\\war-and-peace.txt");

        // 패턴 정리(정규 표현식은 미리 컴파일됨)
        var punctuation = Pattern.compile("\\p{Punct}");
        var whitespace = Pattern.compile("\\s+");
        var words = Pattern.compile("\\w+");

        long startTime = System.currentTimeMillis();
        /*
        // 1. 순차적 읽기
        try  {
            var content = Files.readString(location);
            Map<String, Integer> wordCount =
                    Stream.of(content)
                            // 내용 정리하기 (구두점 제거)
                            .map(punctuation::matcher)
                            .map(matching -> matching.replaceAll(""))
                            // 단어 분할 (Stream<String[]> -> Stream<String>)
                            .map(whitespace::split)
                            .flatMap(Arrays::stream)
                            // 추가 정리(단어 정규식 표현에 맞는 것 집계)
                            .filter(word -> words.matcher(word).matches())
                            // 표준화
                            .map(String::toLowerCase)
                            // 카운트
                            .collect(Collectors.toMap(Function.identity(),
                                    word -> 1,
                                    Integer::sum));
            // Map의 각 요소 출력하기 (단어장)
            for (Map.Entry<String, Integer> entry: wordCount.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
        // 2. 병렬적 읽기
        try (Stream<String> stream = Files.lines(location)) {
            Map<String, Integer> wordCount =
                    // 정리 (구두점 제거)
                    stream.parallel() // 병렬화
                            // 각 작업이 여러 스레드에 분산되어 병렬로 처리됨
                            .map(punctuation::matcher)
                            .map(matching -> matching.replaceAll(""))
                            // 단어 분할 (Stream<String[]> -> Stream<String>)
                            .map(whitespace::split)
                            .flatMap(Arrays::stream)
                            // 추가 정리
                            .filter(word -> words.matcher(word).matches())
                            // 표준화
                            .map(String::toLowerCase)
                            // 카운트
                            .collect(Collectors.toConcurrentMap(Function.identity(),
                                    word -> 1,
                                    Integer::sum));
            // Collector들은 변경 가능한 중간 결과 컨테이너를 공유하기 때문에
            // combiner에서 스레드 안전성이 문제(ConcurrentMidificationException>
            // 따라서 기존과 동일한 인수를 사용하여 스레드를 안전하게 사용하도록 한 버전의 toConcurrentMap 사용
            // Map의 각 요소 출력하기 (단어장)
            for (Map.Entry<String, Integer> entry: wordCount.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        //System.out.println("Sequential Execution Time: " + executionTime + " milliseconds\n");
        System.out.println("Parallel Execution Time: " + executionTime + " milliseconds\n");
    }
}
