package com.example.ch14;

import com.example.ch14.Coffee.*;

public class Functional_Decorator_Pattern_Example {
    public static void main(String[] args) {
        CoffeeMaker coffeeMaker = new BlackCoffeeMaker();

        CoffeeMaker decoratedCoffeeMaker = new AddMilkDecorator(coffeeMaker,
                new MilkCarton());
        Coffee cafaConLeche = decoratedCoffeeMaker.prepare();
        // 데코레이터 패턴으로 블랙커피와 우유를 섞는 카페 콘 레체를 만드는게 쉬워짐
        
        // 함수의 합성으로 여러 데코레이터를 적용하는 방법
        CoffeeMaker decoratedCoffeeMaker2 =
            Barista.decorate(new BlackCoffeeMaker(),
                    coffeeMaker2 -> new AddMilkDecorator(coffeeMaker2,
                            new MilkCarton()));
        CoffeeMaker finalCoffeeMaker =
            Barista.decorate(decoratedCoffeeMaker, AddSugarDecorator::new);
        
        // 정적 팩토리 메서드를 이용해서 추가 재료에 집중하는 방법
        CoffeeMaker maker = Barista.decorate(new BlackCoffeeMaker(),
            Decorations.addMilk(new MilkCarton()),
                Decorations.addSugar()); // 가변적으로 옵션 추가 가능
        var coffee = maker.prepare(); // prepare 호출 과정에서 알아서 
        // decorator들의 prepare 과정으로 채워짐
    }
}
