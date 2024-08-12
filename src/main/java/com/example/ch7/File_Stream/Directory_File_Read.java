package com.example.ch7.File_Stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
// nio는 new I/O로 비동기적, 비차단적 I/O 작업을 수행하는 api 제공
// 주요 특징과 구성 요소로는 buffer, channel, selector, file, charset 등이 있다.
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Directory_File_Read {
    public static void main(String[] args) throws IOException {
        // 상대 경로를 쓸 때는 시스템 설정에서 디폴트 프로젝트 디렉토리 확인
        // 디렉토리 내 파일 순회
        var dir = Paths.get("./src\\main\\java\\com\\example\\ch7\\File_Stream\\text");
        try (var stream = Files.list(dir)) {
            stream.map(Path::getFileName)
                    .forEach(System.out::println);
        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace();
        }
        // try() catch로 try-with-resources 구문을 구현함
        // Stream<T>는 BaseStream을 통해 java.io.AutoCloseable 인터페이스를 구현
        // 따라서 try 블록이 끝날 때 close 메서드가 호출되서 stream이 자동으로 닫힘
        // 일반적으로 메모리 기반 스트림은 close를 호출할 필요가 없으나
        // 파일, 네트워크 스트림, 병렬 스트림 처럼 리소스를 관리해야 하는 경우
        // try-with-resources를 써야함
        // java.nio.file.Files의 모든 스트림 관련 메서드는 IOException을 던짐
        System.out.println();
        
        // 깊이 우선 디렉토리 순회
        var start = Paths.get("./src");
        try (var stream2 = Files.walk(start)) {
            stream2.filter(Files::isDirectory)  // Files.isDirectory로 디렉토리만 필터링
                    .sorted(Comparator.naturalOrder())  // 사전순 정렬
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        
        // 파일 시스템 탐색
        BiPredicate<Path, BasicFileAttributes> matcher =
                (path, attr) -> attr.isRegularFile();
        try (var stream = Files.find(start, Integer.MAX_VALUE, matcher)) {
            stream.sorted().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        
        // 파일 한 줄씩 읽기 (Files.lines)
        var location = Paths.get("./src\\main\\java\\com\\example\\ch7\\Stream_Usage_Example.java");

        // 패턴 정리(정규 표현식은 미리 컴파일됨)
        var punctuation = Pattern.compile("\\p{Punct}");
        var whitespace = Pattern.compile("\\s+");
        var words = Pattern.compile("\\w+");

        try (Stream<String> stream = Files.lines(location)) {
            Map<String, Integer> wordCount =
                    // 정리 (구두점 제거)
            stream.map(punctuation::matcher)
                    .map(matching -> matching.replaceAll(""))
                    // 단어 분할 (Stream<String[]> -> Stream<String>)
                    .map(whitespace::split)
                    .flatMap(Arrays::stream)
                    // 추가 정리
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
        System.out.println();
    }
}
