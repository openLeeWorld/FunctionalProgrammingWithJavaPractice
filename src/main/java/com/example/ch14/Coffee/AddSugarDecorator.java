package com.example.ch14.Coffee;

import java.util.ArrayList;
import java.util.List;

public class AddSugarDecorator extends Decorator{

    //private final Sugar sugar;

    public AddSugarDecorator(CoffeeMaker target) {
        super(target);
    } // 생성자

    @Override
    public List<String> getIngredients() {
        var newIngredients = new ArrayList<>(super.getIngredients());
        newIngredients.add("Sugar");
        return newIngredients;
    }

    @Override
    public Coffee prepare() {
        // 커피 종류 준비
        return super.prepare();
    }
}
