package com.example.ch8;

import java.io.IOException;
import java.util.stream.Stream;

public class Immutable_Accumulation {
    public static void main(String[] args) throws IOException {
        // for-loop을 활용한 가변 누적(지역 외부의 가변 상태 활용)
        // 스트림을 활용한 숫자의 불변 누적
        int total = Stream.of(1, 2, 3, 4, 5 ,6)
                .parallel()
                .reduce(0, Integer::sum);
        // 축소가 associativity가 있다면 누적기 인수의 순서나 그룹화가 
        // 최종 결과에 영향을 미치지 않는다는 것
        System.out.println(total);
    }
    
}
