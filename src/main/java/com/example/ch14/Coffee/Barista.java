package com.example.ch14.Coffee;

import java.util.Arrays;
import java.util.function.Function;

public final class Barista {
    // 스트림을 사용해 축소를 통한 다중 데코레이션 구현
    // 바리스타가 다양한 커피 데코레이션을 구현
    @SafeVarargs
    public static
    CoffeeMaker decorate(CoffeeMaker coffeeMaker,
                         Function<CoffeeMaker, CoffeeMaker>... decorators) {
        Function<CoffeeMaker, CoffeeMaker> reducedDecorations =
            Arrays.stream(decorators)
                .reduce(Function.identity(),
                    Function::andThen);
        // 데코레이션들은 Stream<Function<.,.>>을 사용해 각각을 결합하여
        // 모든 요소를 하나의 Function<CoffeeMaker, CoffeeMaker>로 축소
        return reducedDecorations.apply(coffeeMaker);
    }
    
    private Barista() {
        // 기본 생성자 생략
    }
}
