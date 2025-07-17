package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum TransactionType {
    INCOME("INCOME"),
    EXPENSE("EXPENSE"),
    GOAL("GOAL"),
    TRANSFER("TRANSFER");
    private final String value;
    TransactionType(String value){
        this.value = value;
    }
}
