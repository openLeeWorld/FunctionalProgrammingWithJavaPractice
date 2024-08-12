package com.example.ch2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lambda_Example {
    public static void main(String[] args) {
        //runnable로 람다 실행하기
        Runnable runnable = () -> System.out.println("Hello, World!");
        new Thread(runnable).start();

        // sort함수로 람다 쓰기
        String[] words = {"apple_banana", "banana", "ch"};
        Arrays.sort(words, (s1, s2) -> s1.length() - s2.length()); // 많은 글자를 앞으로
        System.out.println(Arrays.toString(words));

        // Predicate를 람다로 실행하기
        List<String> names = new ArrayList<>();
        names.add("John");
        names.add("Jerry");
        names.add("Anna");
        names.removeIf(name -> name.startsWith("J"));
        System.out.println(names);

        // stream API로 작성하기
        List<String> new_names= Arrays.asList("John", "Jane", "Jack", "Doe", "Smith");
        List<String> filteredNames = new_names.stream()
                .filter(name -> name.startsWith("J"))
                .collect(Collectors.toList());
        System.out.println(filteredNames);
    }
}
