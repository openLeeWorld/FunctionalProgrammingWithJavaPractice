package com.example.ch10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Functional_Exception_Handling {
    public static void main(String[] args) {
        // 람다를 블록으로 변환하여 try-catch 블록 사용
        var path1 = Paths.get("./src\\main\\java\\com\\example\\ch10\\ch10.txt");
        var path2 = Paths.get("./"); // 없는 파일

        Stream.of(path1, path2)
                .map(path -> {
                    try {
                        return Files.readString(path);
                    } catch (IOException e) {
                        return null;
                    }
                })
                .forEach(System.out::println);
        System.out.println();

        // 다음은 safeMethodExtraction을 차용한 방법(null 파일 제외)
        var Excep = new Functional_Exception_Handling();
        Excep.PrintWithSafeReadString(path1, path2);

        // 다음은 반환 타입으로써 Result<V, E>를 활용하는 것이다.
        Excep.PrintWithSafeReadString2(path1, path2);

        /*
        // 다음은 ThrowingFunction<T, R>을 활용한 방법
        ThrowingFunction<Path, String> throwingFn = Files::readString;
        Stream.of(path1, path2)
                .map(ThrowingFunction.uncheck(Files::readString))
                .filter(Objects::nonNull)
                .forEach(System.out::println);
        // 어떠한 예외가 발생하더라도 스트림 파이프라인은 중단(RuntimeException)될 것이며,
        // 로컬화된 예외 처리도 하지 않음
         */
        
        // 성공한 케이스만 다루기
        Excep.SuccessCase(path1, path2);
        // 성공과 실패한 케이스 모두 다루기
        Excep.SuccessAndFailureCase(path1, path2);

        /*
        // 새로운 파이프라인 생성
        var trySuccessFailure = Try.<Path, String> of(Files::readString)
                .success(String::toUpperCase)
                .failure(str -> null)
                .apply(path1);
        // 스트림과 다르게 각 연산들은 독립적이며 연속되지 않음
        // 성공인지 실패인지는 Try의 평가 상태에 따라 결정됨
         */
        
        // 고차함수에서도 Try파이프라인을 쓸 수 있게 Function<T, Optional<R>>을 구현
        Function<Path, Optional<String>> fileLoader = Try.<Path, String> of(Files::readString)
                .success(String::toUpperCase)
                .failure(str -> null);

        List<String> fileContents = Stream.of(path1, path2)
                .map(fileLoader)
                .flatMap(Optional::stream)
                .toList();
        System.out.println(fileContents);
    }

    private String safeReadString(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            return null;
        }
    }

    private void PrintWithSafeReadString(Path path1, Path path2) {
        Stream.of(path1, path2)
                .map(this::safeReadString)
                .filter(Objects::nonNull)
                .forEach(System.out::println);
        System.out.println();
    }

    Result<String, IOException> safeReadString2(Path path) {
        try { // Result 레코드로 값으로서의 에러 구현
            return Result.success(Files.readString(path));
        } catch (IOException e) {
            return Result.failure(e);
        }
    }

    private void PrintWithSafeReadString2(Path path1, Path path2) {
        Stream.of(path1, path2)
                .map(this::safeReadString2)
                .filter(Result::isSuccess)
                .map(Result::value)
                .forEach(System.out::println);
        System.out.println();
    }

    private void SuccessCase(Path path1, Path path2) {
        Stream.of(path1, path2)
                .map(this::safeReadString2)
                .map(result -> result.mapSuccess(String::toUpperCase))
                .flatMap(Optional::stream)
                .forEach(System.out::println);
        System.out.println();
    }

    private void SuccessAndFailureCase(Path path1, Path path2) {
        Stream.of(path1, path2)
                .map(this::safeReadString2)
                .map(result -> result.map(
                    success-> success.toUpperCase(),
                    failure -> "IO-Error: " + failure.getMessage()))
                .forEach(System.out::println);
        System.out.println();
    }
}
