package com.example.ch14.Coffee;

import java.util.List;

public interface CoffeeMaker {
    List<String> getIngredients();
    Coffee prepare();
}




