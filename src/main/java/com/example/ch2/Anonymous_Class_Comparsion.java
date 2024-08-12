package com.example.ch2;

public class Anonymous_Class_Comparsion {
    public static void main(String[] args) {
        // 익명 클래스
        var helloWorld = new HelloWorld() {
            @Override
            public String sayHello(String name) {
                return "hello," + name + "!";
            }
        };

        String hello = helloWorld.sayHello("너");
        System.out.println(hello);

        // 람다
        HelloWorld helloWorldLambda = name -> "hello, " + name + "!";

        String hello_lambda = helloWorldLambda.sayHello("Alice");
        System.out.println(hello_lambda);
    }
    
    // 함수형 인터페이스
    @FunctionalInterface
    interface HelloWorld {
        String sayHello(String name);
    }
}
