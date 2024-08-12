package com.example.ch3;

import java.util.function.Function;

public class Composite_Function_example {
    public static void main(String[] args) {
        Function<String, String> removeLowerCaseA = str -> str.replace("a", "");
        Function<String, String> upperCase = String::toUpperCase;

        var input = "abcd";

        System.out.println(removeLowerCaseA.andThen(upperCase)
                .apply(input));


        System.out.println(upperCase.compose(removeLowerCaseA)
                .apply(input));
    }
}
