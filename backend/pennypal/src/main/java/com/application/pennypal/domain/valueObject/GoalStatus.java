package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum GoalStatus {
    ACTIVE("ACTIVE"),
    COMPLETED("COMPLETED"),
    EXPIRED("EXPIRED"),
    CANCELLED("CANCELLED");
    private final String value;
    GoalStatus(String value){
        this.value = value;
    }
}
