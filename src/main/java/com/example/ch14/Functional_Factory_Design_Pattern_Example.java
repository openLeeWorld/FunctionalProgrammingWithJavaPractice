package com.example.ch14;

import com.example.ch14.Shape.ShapeType;

import java.awt.*;
import java.util.function.Function;

import com.example.ch14.Shape.Shape;

public class Functional_Factory_Design_Pattern_Example {
    public static void main(String[] args) {
        // 다른 함수를 선언해서 객체 속성 출력하기 
        var redCircle = ShapeType.CIRCLE.newInstance(Color.RED);
        PrintAttributesOfShape(redCircle);

        // 함수형으로 출력하기
        Function<Shape, Shape> cornerPrint =
            shape -> {
                System.out.println("corners of shape: " + shape.corners());
                return shape;
        };
        ShapeType.SQUARE.factory.andThen(cornerPrint).apply(Color.BLUE);
        // apply 함수를 나중에 적용
    }

    private static void PrintAttributesOfShape(Shape shape) {
        System.out.println("corners of Shape: " + shape.corners());
        System.out.println("Color of Shape: " + shape.color());
        System.out.println("Type of Shape: " + shape.type());
    }
}
