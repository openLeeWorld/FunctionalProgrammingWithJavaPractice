package com.example.ch12;

import java.util.Stack;
import java.util.function.Consumer;

// 트리 노드 구조
public record Node<T>(T value, Node<T> left, Node<T> right) {
    public static <T> Node<T> of(T value, Node<T> left, Node<T> right) {
        return new Node<>(value, left, right);
    }
    
    public static <T> Node<T> of(T value) {
        return new Node<>(value, null, null);
    } // 루트 노트 정적 헬퍼 메서드

    public static <T> Node<T> left(T value, Node<T> left) {
        return new Node<>(value, left, null);
    } // 자식이 왼쪽만 있는 경우

    public static <T> Node<T> right(T value, Node<T> right) {
        return new Node<>(value, null, right);
    } // 자식이 오른쪽만 있는 경우
    
    public static <T> void traverseIterative(Node<T> root) {
        var tmpNodes = new Stack<Node<T>>(); // 중위 순회를 위한 스택
        var current = root;

        while(!tmpNodes.isEmpty() || current != null) {
            if (current != null) {
                tmpNodes.push(current);
                current = current.left();
                continue;
            } // 왼쪽으로 계속 내려가면서 push 하고
            current = tmpNodes.pop();
            System.out.print(current.value);
            current = current.right();
        }
        System.out.println();
    }

    public static <T> void traverseRecursive(Node<T> node) {
        if (node == null) {
            return;
        }
        traverseRecursive(node.left());
        System.out.print(node.value());
        traverseRecursive(node.right());
    }

    public static <T> void traverseFunctional(Node<T> node, Consumer<T> fn) {
        if (node == null) {
            return;
        }
        traverseFunctional(node.left(), fn);
        fn.accept(node.value());
        traverseFunctional(node.right(), fn);
    }

    public void traverse(Consumer<T> fn) {
        Node.traverseFunctional(this, fn);
        System.out.println();
    }
}
