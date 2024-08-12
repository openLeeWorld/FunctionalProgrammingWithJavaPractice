package com.example.ch14.Coffee;

import java.util.List;

public abstract class Decorator implements CoffeeMaker{
    // Decorator는 CoffeeMaker를 상속하므로 대체 기능하는 추상 클래스
    // 추가적으로 커피 메이커가 구현해야 할 기능을 넣으면 됨
    
    private final CoffeeMaker target;

    protected Decorator(CoffeeMaker target) {
        this.target = target;
    } // 원래 장식되어야 할 CoffeeMaker 인스턴스

    @Override
    public List<String> getIngredients() {
        return this.target.getIngredients();
    } // Decorator는 super호출을 통해 원래의 결과를 얻을 수 있음

    @Override
    public Coffee prepare() {
        return this.target.prepare();
    }  // Decorator는 super호출을 통해 원래의 결과를 얻을 수 있음
}
