package com.example.ch2;

import java.time.LocalDate;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

public class Method_Reference {
    public static void main(String[] args) {
        // 람다 (정적 메서드 참조)
        Function<Integer, String> asLambda = i -> Integer.toHexString(i);

        // 1. 정적 메서드 참조
        Function<Integer, String> asRef = Integer::toHexString;

        // 2. 바운드 비정적 메서드 참조 (특정 객체에 바운드)
        Predicate<LocalDate> isAfterNowAsRef = LocalDate.now()::isAfter;

        // 정적 필드 바인딩
        Function<Object, String> castToStr = String.class::cast;
        
        // 상위 클래스와 하위 클래스 this, super 바인딩
        new SubClass().superAndThis("hello, World");

        // 3. 언바운드 비정적 메서드 참조 (특정 타입 인스턴스 메서드)
        Function<String, String> toLowerCaseRef = String::toLowerCase;
        
        // 4. 생성자 참조 람다
        Function<String, Locale> newLocaleLambda = language -> new Locale(language);

        // 생성자 참조
        Function<String, Locale> newLocaleRef = Locale::new;
    }

    public static class SuperClass {
        public String doWork(String input) {
            return "super: " + input;
        }
    }

    public static class SubClass extends SuperClass {
        @Override
        public String doWork(String input) {
            return "this: " + input;
        }

        public void superAndThis(String input) {
            Function<String, String> thisWorker = this::doWork;
            var thisResult = thisWorker.apply(input); // apply로 Function 적용
            System.out.println(thisResult);

            Function<String, String> superWorker = SubClass.super::doWork;
            var superResult = superWorker.apply(input);
            System.out.println(superResult);
        }
    }
}


