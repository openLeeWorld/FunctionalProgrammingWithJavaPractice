package com.example.ch7;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Date_Time_Example {
    public static void main(String[] args) {
        boolean isItTeaTime = LocalDateTime.now()
                .query(temporal -> {
                    var time = LocalTime.from(temporal);
                    return time.getHour() >= 16;
                });
        System.out.println("is it Tea time?: " + isItTeaTime);
        LocalTime time = LocalDateTime.now().query(LocalTime::from);
        System.out.println("current time: " + time);

    }
}
