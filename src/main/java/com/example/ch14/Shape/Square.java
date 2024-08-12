package com.example.ch14.Shape;

import java.awt.*;

public record Square(Color color) implements Shape{
    @Override
    public int corners() {
        return 4;
    }

    @Override
    public ShapeType type() {
        return ShapeType.SQUARE;
    }
}
