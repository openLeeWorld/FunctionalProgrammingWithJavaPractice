package com.example.ch3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Functional_Interface_example {
    public static void main(String[] args) {
        /*
            @FunctionalInterface
            public interface Function<T, R> {
                R apply (T t);
            }
        */
        Function<String, Integer> stringLength = str -> str != null ? str.length() : 0;
        Integer result = stringLength.apply("Hello, Function");
        System.out.println(result);

        /*
            @FunctionalInterface
            public interface Consumer<T> {
                void accept(T t);
            }
        */
        Consumer<String> println = str -> System.out.println(str);
        println.accept("Hello, Consumer");

        /*
            @FunctionalInterface
            public interface Supplier<T> {
                T get();
            }
        */
        Supplier<Double> random = () -> Math.random();
        Double Doubleresult = random.get();
        System.out.println(Doubleresult);

        /*
            @FunctionalInterface
            public interface Predicate<T> {
                boolean test(T t);
            }
        */
        Predicate<Integer> over9000 = i -> i > 9_000;
        boolean result2 = over9000.test(12_345);
        System.out.println(result2);
    }
}
