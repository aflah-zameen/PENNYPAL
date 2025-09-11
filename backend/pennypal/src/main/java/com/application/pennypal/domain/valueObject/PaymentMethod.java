package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    WALLET("WALLET"),
    CARD("CARD"),
    ADMIN_CARD("ADMIN_CARD"),
    STRIPE("STRIPE");
    private final String value;
    PaymentMethod(String value){
        this.value = value;
    }
}
