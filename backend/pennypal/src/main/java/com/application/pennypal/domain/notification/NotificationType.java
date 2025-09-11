package com.application.pennypal.domain.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    GOAL_WITHDRAW("GOAL_WITHDRAW"),
    LENDING("LENDING"),
    LOAN_CASE("LOAN_CASE"),
    USER_SUSPENSION("USER_SUSPENSION"),
    CHAT("CHAT");
    private final String value;
    NotificationType(String value){
        this.value = value;
    }
}

