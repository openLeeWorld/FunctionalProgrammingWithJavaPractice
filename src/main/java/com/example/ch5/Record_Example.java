package com.example.ch5;

import java.time.LocalDateTime;

public class Record_Example {
    public static void main(String[] args) {
        // 레코드 생성자
        var user = new User("ben", true, LocalDateTime.now());
        var username = user.username();
        System.out.println(username);

        // 빌더 객체
        var builder = new User.UserBuilder("gwen")
                .active(false)
                .lastLogin(LocalDateTime.now());
        var user_builder = builder.build();
        var username_builder = user_builder.username();
        System.out.println(username_builder);

        // Time 레코드로 데이터 값 정규화해서 생성하기
        var time = new Time(12, 67);
        System.out.println(time); // Time[minutes=13, seconds=7]

        // Point 레코드에서 변형된 복사본 생성
        var point = new Point(23, 42);
        var newPoint_x = point.withX(5);
        var newPoint_y = newPoint_x.withY(5);
        System.out.println(newPoint_y);

        // Point 중첩 레코드 사용 (wither 패턴)
        var sourcePoint = new Point(41, 25);
        var modifiedPoint = sourcePoint.with().x(5);
        System.out.println(modifiedPoint);
        
        // Point 빌더 패턴 사용
        var original = new Point(23, 42);
        var updated = new Point.Builder(original)
                .x(5)
                .y(10)
                .build();

        System.out.println(updated);

        // @RecordBuilder가 자동으로 PointNewBuilder 생성
        var original_new = PointNewBuilder.builder()
                .x(5)
                .y(23)
                .build();

        var modified_new = PointNewBuilder.builder(original_new)
                .x(12)
                .build();

        System.out.println(modified_new);

        var modified1 = original_new.withX(4);
        System.out.println(modified1);

        var modified2 = original_new.with().x(19).y(14).build();
        System.out.println(modified2);

        // consumer를 통한 다중 변경(build()호출이 필요하지 않음)
        var modified3 = original_new.with(builder_new -> builder_new.x(15).y(21));
        System.out.println(modified3);
    }
}

