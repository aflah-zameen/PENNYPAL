package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum RecurringLogStatus {
    PENDING("PENDING"),
    RECEIVED("RECEIVED");
    private final String value;
    RecurringLogStatus(String value){
        this.value = value;
    }
}
