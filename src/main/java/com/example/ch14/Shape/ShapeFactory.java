package com.example.ch14.Shape;

import java.awt.*;
import java.util.Objects;

public class ShapeFactory {

    public static Shape newShape(ShapeType type, Color color) {
        Objects.requireNonNull(color);

        return switch (type) {
            case CIRCLE -> new Circle(color);
            case TRIANGLE -> new Triangle(color);
            case SQUARE -> new Square(color);
            case PENTAGON -> new Pentagon(color);
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
