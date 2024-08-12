package com.example.ch5;

public record Time(int minutes, int seconds) {

    public Time {
        if (seconds >= 60) {
            int additionalMinutes = seconds / 60 ;
            minutes += additionalMinutes;
            seconds -= additionalMinutes * 60;
        }
    } // 간결 생성자에 데이터 범위를 초과하는 값을 정규화
}
