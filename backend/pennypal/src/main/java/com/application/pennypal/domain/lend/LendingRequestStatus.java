package com.application.pennypal.domain.lend;

import lombok.Getter;

@Getter
public enum LendingRequestStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    MODIFIED("MODIFIED"),
    TRANSFERRED("TRANSFERRED");

    private final String value;
    LendingRequestStatus(String value){
        this.value = value;
    }
}
