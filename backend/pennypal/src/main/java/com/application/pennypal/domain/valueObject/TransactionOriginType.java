package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum TransactionOriginType {
    INCOME("INCOME"),
    EXPENSE("EXPENSE"),
    GOAL("GOAL"),
    TRANSFER("TRANSFER");
    private final String value;
    TransactionOriginType(String value){
        this.value = value;
    }
}
