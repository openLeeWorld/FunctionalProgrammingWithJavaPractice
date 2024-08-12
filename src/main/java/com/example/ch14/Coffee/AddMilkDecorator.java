package com.example.ch14.Coffee;

import java.util.ArrayList;
import java.util.List;

public class AddMilkDecorator extends Decorator {
    private final MilkCarton milkCarton;

    public AddMilkDecorator(CoffeeMaker target,
                            MilkCarton milkCarton) {
        super(target);
        this.milkCarton = milkCarton;
    } // 데코레이터를 활용한 우유 추가(커피메이커와 우유갑)

    @Override
    public List<String> getIngredients() {
        var newIngredients = new ArrayList<>(super.getIngredients());
        newIngredients.add("Milk");
        return newIngredients;
    }

    @Override
    public Coffee prepare() {
        var coffee = super.prepare(); // 커피 종류 준비
        coffee = this.milkCarton.pourInto(coffee); // 우유를 자잇ㄱ으로 추가
        return coffee;
    }
}
