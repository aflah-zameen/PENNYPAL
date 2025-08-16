package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum GoalWithdrawRequestStatus {
    PENDING("PENDING"),
    REJECTED("REJECTED"),
    APPROVED("APPROVED");

    private final String value;
    GoalWithdrawRequestStatus(String value){
        this.value = value;
    }
}
