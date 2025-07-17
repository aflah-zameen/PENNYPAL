package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum IncomeStatus {
    PENDING("PENDING"), // If the income in future date or user not confirm the income.
    COMPLETED("COMPLETED"),
    RECURRING("RECURRING"),// After successful transaction.
    CANCELLED("CANCELLED"),  // If user deleted income.
    INACTIVE("INACTIVE"); // If user make income pause.

    private final String value;
    IncomeStatus(String value){
        this.value = value;
    }
}
