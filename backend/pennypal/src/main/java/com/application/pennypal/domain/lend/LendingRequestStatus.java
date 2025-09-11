package com.application.pennypal.domain.lend;

import lombok.Getter;

@Getter
public enum LendingRequestStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    CANCELLED("CANCELLED"),
    REJECTED("REJECTED");

    private final String value;
    LendingRequestStatus(String value){
        this.value = value;
    }
}
