package com.example.ch14.Shape;

import java.awt.*;

public record Circle(Color color) implements Shape{
    @Override
    public int corners() {
        return 0;
    }

    @Override
    public ShapeType type() {
        return ShapeType.CIRCLE;
    }
}
