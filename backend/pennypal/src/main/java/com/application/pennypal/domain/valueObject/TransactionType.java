package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum TransactionType {
    INCOME("INCOME"),
    EXPENSE("EXPENSE"),
    GOAL("GOAL"),
    TRANSFER("TRANSFER"),
    SUBSCRIPTION("SUBSCRIPTION"),
    REWARD("REWARD"),
    WALLET("WALLET");
    private final String value;
    TransactionType(String value){
        this.value = value;
    }
}
