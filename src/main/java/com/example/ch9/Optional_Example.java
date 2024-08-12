package com.example.ch9;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Optional_Example {
    public static void main(String[] args) {
        // Optional 값 생성
        Optional<String> maybeValue = Optional.of("not null"); // null이면 에러
        Optional<String> NotNullValue = Optional.ofNullable("NotNull");
        Optional<String> NullValue = Optional.empty(); // null
        
        // 자세한 Optional 값 확인하기
        if (maybeValue.isPresent()) {
            var value = maybeValue.orElseThrow();
            System.out.println(value);
        } else {
            System.out.println("No value found!");
        }
        // 단순한 Optinal value 확인
        maybeValue.ifPresentOrElse(System.out::println,
                () -> System.out.println("No Value found!"));
        
        // Permission 레코드로 Optional pipeline 구축
        Permissions permissions = new Permissions(List.of("admin"), 
                new Permissions.Group(Optional.of(new Permissions.User(true))));

        // 다음 코드를 if문으로 바꾸면 순환 복잡도가 증가
        boolean isActiveAdmin =
                Optional.ofNullable(permissions)
                        // Optional 클래스의 빌더 메서드 체인
                        .filter(Predicate.not(Permissions::isEmpty))
                        // 빈 permission 제외
                        .map(Permissions::group)
                        // group 데이터 가져옴
                        .flatMap(Permissions.Group::admin)
                        // admin이 존재하지 않은 경우, Optional 중첩을 해제
                        .map(Permissions.User::isActive)
                        // 활성상태인 유저들만 가져옴
                        .orElse(Boolean.FALSE);
                        // 비어있는 Optional을 반환시 False를 할당
        System.out.println(isActiveAdmin);

        // flatMap을 사용한 스트림 요소로써의 Optional
        List<Permissions> permissionsList = List.of(permissions);

        List<Permissions.User> activeUsers =
                permissionsList.stream()
                        .filter(Predicate.not(Permissions::isEmpty))
                        .map(Permissions::group)
                        .map(Permissions.Group::admin)
                        .flatMap(Optional::stream)
                        // 다음을 대체함
                        // .filter(Optional::isPresent)
                        // .map(Optional::orElseThrow)
                        .filter(Permissions.User::isActive)
                        .toList();
        System.out.println(activeUsers);

    }
}
