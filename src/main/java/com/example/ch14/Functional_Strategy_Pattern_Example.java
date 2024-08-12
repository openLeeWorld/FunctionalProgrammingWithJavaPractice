package com.example.ch14;

import com.example.ch14.Shipping.Parcel;
import com.example.ch14.Shipping.ShippingService;
import com.example.ch14.Shipping.ShippingStrategies;

public class Functional_Strategy_Pattern_Example {
    public static void main(String[] args) {
        // 사용 방법
        Parcel parcel = new Parcel();
        ShippingService.ship(parcel, ShippingStrategies::standardShipping);
    }
}
