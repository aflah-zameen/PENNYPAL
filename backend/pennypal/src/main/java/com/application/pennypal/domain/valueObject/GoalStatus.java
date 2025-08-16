package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum GoalStatus {
    ACTIVE("ACTIVE"),
    COMPLETED("COMPLETED"),
    EXPIRED("EXPIRED"),
    WITHDRAW_PENDING("WITHDRAW_PENDING"),
    WITHDRAW_COLLECTED("WITHDRAW_COLLECTED"),
    WITHDRAW_REJECTED("WITHDRAW_REJECTED"),
    CANCELLED("CANCELLED");
    private final String value;
    GoalStatus(String value){
        this.value = value;
    }
}
