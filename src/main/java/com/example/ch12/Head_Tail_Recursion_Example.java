package com.example.ch12;

public class Head_Tail_Recursion_Example {
    public static void main(String[] args) {
        System.out.println(factorialHead(4L));
        System.out.println(factorialTail(4L, 1L));
        System.out.println();

        // 트리 구조 생성
        var root = Node.of("1",
                    Node.of("2",
                            Node.of("4",
                                    Node.of("7"),
                                    Node.of("8")),
                            Node.of("5")),
                    Node.right("3",
                            Node.left("6",
                                Node.of("9"))));

        Node.traverseIterative(root);
        Node.traverseRecursive(root);
        System.out.println();
        root.traverse(System.out::print);
    }

    static long factorialHead(long n) {
        if (n == 1L) {
            return 1L;
        }
        var nextN = n - 1L;

        return n * factorialHead(nextN);
    }

    static long factorialTail(long n, long accumulator) {
        if (n == 1L) {
            return accumulator;
        }
        var nextN = n - 1L;
        var nextAccumulator = n * accumulator;

        return factorialTail(nextN, nextAccumulator);
    } // 자바는 꼬리 호출 최적화를 지원하지 않음

}
