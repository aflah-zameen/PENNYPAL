package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    FAILED("FAILED");

    private final String value;
    TransactionStatus(String value){
        this.value =value;
    }
}
