package com.example.ch14.Shape;

import java.awt.*;

public record Triangle(Color color) implements Shape{
    @Override
    public int corners() {
        return 3;
    }

    @Override
    public ShapeType type() {
        return ShapeType.TRIANGLE;
    }
}
