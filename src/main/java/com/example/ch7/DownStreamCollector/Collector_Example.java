package com.example.ch7.DownStreamCollector;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Collector_Example {
    public static void main(String[] args) {
        // 요소 변환 (유저 데이터를 그룹당 유저 UUID로)
        List<User> users = generateRandomUsers(10);  // 10명의 유저 생성
        
        Map<String, Set<UUID>> lookup = // group당 유저들
                users.stream()
                    .collect(groupingBy(User::group, //group으로 묶음
                        mapping(User::id, toSet()))); // id로 mapping함

        // 그룹 키를 기준으로 정렬하여 출력
        printMapElement(lookup);

        // 요소 축소(reduce)
        var downstream = Collectors.reducing(0, // 초기값
                (User user) -> user.logEntries().size(), // 매퍼(User 요소를 원하는 값(유저당 로그 size)으로 변환
                Integer::sum); // 명령 코드(축소 연산)

        Map<UUID, Integer> logCountPerUserId = users.stream()
            .collect(groupingBy(User::id, downstream));

        printMapElement_UUID(logCountPerUserId);
        //printMapElement(logCountPerUserId);

        // 컬렉션 평탄화
        var downstream2 = Collectors.flatMapping((User user) ->
                user.logEntries().stream(), Collectors.toList());
        Map<String, List<String>> result = users.stream()
            .collect(Collectors.groupingBy(User::group, downstream2));
        // 그룹 키를 기준으로 정렬하여 출력
        printMapElement(result);

        // 요소 필터링
        var startOfDay = LocalDate.now().atStartOfDay(); // 이 날짜의 정오로 만듬
        Predicate<User> loggedInToday =
                Predicate.not(user -> user.lastLogin().isBefore(startOfDay));
        // 컬렉터 필터 사용(filtering)
        Map<String, Set<UUID>> todaysLoginsByGroupWithFilteringCollector =
            users.stream()
                .collect(groupingBy(User::group, // grouping
                    filtering(loggedInToday, // predicate
                        mapping(User::id, toSet())))); // downstream
        // 그룹 키를 기준으로 정렬하여 출력
        printMapElement(todaysLoginsByGroupWithFilteringCollector);

        // 합성 컬렉터(Collectors.teeing)
        UserStats result_stat = users.stream()
                .collect(Collectors.teeing(Collectors.counting(), // 첫번째 downstream은 요소의 수를 카운팅
                        Collectors.filtering(user-> user.lastLogin() == null, // 두번째 downstream은 특정 요건으로 요소를 필터링 후, 남은 요소를 셈
                                Collectors.counting()), // 여기까지 두번째 downstream
                        UserStats::new)); // merger 함수
        System.out.println("total: " + result_stat.total());
        System.out.println("neverLoggedIn: " + result_stat.neverLoggedIn());
    }

    public record UserStats(long total, long neverLoggedIn) {
    } // 자바가 동적 튜플을 지원 안하므로 로컬 레코드를 사용

    public static List<User> generateRandomUsers(int count) {
        List<User> users = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();

            String group = "Group-" + (random.nextInt(5) + 1);  // Group-1 to Group-5
            LocalDateTime lastLogin = LocalDateTime.now().minusDays(random.nextInt(30));
            List<String> logEntries = generateRandomLogEntries(random.nextInt(5) + 1);  // 1 to 5 log entries

            users.add(new User(id, group, lastLogin, logEntries));
        }

        return users;
    }

    public static List<String> generateRandomLogEntries(int count) {
        List<String> logEntries = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            logEntries.add("Log entry " + (i + 1));
        }
        return logEntries;
    }

    /*
    public static void printMapElement(Map<? extends Serializable, ?> result) {
        // 타입 안정성을 위해 명시적 타입 캐스팅을 사용(custom comparator)
        // String, UUID 모두 Serializable을 확장하지만, 후자는 Comparable을 확장하지 않음
        // 키를 Comparable로 캐스팅함, String, UUID 모두 Comparable을 구현하기 때문에 안전함
        // 컴파일러 경고를 끄고 싶다면 extends <각 타입>으로 각각 구현할 것
        Comparator<Map.Entry<? extends Serializable, ?>> comparator =
                (e1, e2) -> {
                    Comparable<Object> key1 = (Comparable<Object>) e1.getKey();
                    Object key2 = e2.getKey();
                    return key1.compareTo(key2);
                };
        
        // 그룹 키를 기준으로 정렬하여 출력
        result.entrySet().stream()
                .sorted(comparator) // 키(그룹명)으로 정렬
                .forEach(entry -> {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                });
        System.out.println();
    }
     */

    public static void printMapElement(Map<? extends String, ?> result) {
        // 그룹 키를 기준으로 정렬하여 출력
        result.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 키(그룹명)으로 정렬
                .forEach(entry -> {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                });
        System.out.println();
    }

    public static void printMapElement_UUID(Map<? extends UUID, ?> result) {
        // 그룹 키를 기준으로 정렬하여 출력
        result.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 키(그룹명)으로 정렬
                .forEach(entry -> {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                });
        System.out.println();
    }
}
