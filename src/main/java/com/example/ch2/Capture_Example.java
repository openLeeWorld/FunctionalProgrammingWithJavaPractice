package com.example.ch2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Capture_Example {
    public static void main(String[] args) {
        capture();
        
        // 아래는 final 변수 데이터 변경하기
        final List<String> wordList = new ArrayList<>();
        // 컴파일 성공
        Runnable addItemInLambda = () -> wordList.add("adding is fine");
        // 컴파일 실패
        // wordList = List.of("assigning");

        // 아래는 Effectively final을 참조를 만드는 방식이다.
        var nonEffectivelyFinal = 1_000L; // 이 시점에서는 effectively final
        nonEffectivelyFinal = 9_000L; // 변수 변경해서 람다에서 사용 불가
        var finalAgain = nonEffectivelyFinal; // 새 변수 초기화 후 변경 안하면 기존 자료구조에 대한 참조를 다시 final로 만드는 효과
        Predicate<Long> is0ver9000 = input -> input > finalAgain; // 캡쳐 다시 가능
    }

    static void capture() {
        var theAnswer = 42;
        Runnable printAnswer =
                () -> System.out.println("the answer is " + theAnswer);
        // 람다 표현식 printAnswer는 그 변수를 바디 내에서 캡쳐
        run(printAnswer);
        // 이 람다 표현식은 다른 메서드와 스코프에서 실행될 수 있지만 변수 theAnswer에도
        // 여전히 접근할 수 있다.
    }

    static void run(Runnable r) {
        r.run();
    }
    /*
    JVM은 실제 사용패턴에 기반하여 다양한 전략으로 람다를 최적화함.
    변수가 캡쳐되지 않은 경우, 람다는 내부적으로 간단한 정적 메서드가 될 수 있으며,
    변수가 캡쳐되는 상황에서 추가적인 객체 할당이 발생하며, 이는 성능 및 가비지 컬렉터 시간에 영향을
    줄 수 있다. 변수 캡쳐는 해당 변수가 Effectively final이어야 한다.
     */
}
