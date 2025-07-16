package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum RecurringIncomeLogStatus {
    PENDING("PENDING"),
    RECEIVED("RECEIVED");
    private final String value;
    RecurringIncomeLogStatus(String value){
        this.value = value;
    }
}
