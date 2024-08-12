package com.example.ch14.Shape;

import java.awt.*;
import java.util.Objects;
import java.util.function.Function;

public enum ShapeType {
    CIRCLE(Circle::new),
    TRIANGLE(Triangle::new),
    SQUARE(Square::new),
    PENTAGON(Pentagon::new);

    public final Function<Color, Shape> factory;

    ShapeType(Function<Color, Shape> factory) {
        this.factory = factory;
    } // enum 각 타입의 생성자

    public Shape newInstance(Color color) {
        Objects.requireNonNull(color);
        return this.factory.apply(color);
    }
    
    // 자바 컴파일러는 enum에 새로운 항목이 추가될 때마다 
    // 팩토리를 구현하도록 강제
    // boilerplate를 줄이고 추후 확장성에 대한 컴파일 시간 안정성을 높여줌
}
