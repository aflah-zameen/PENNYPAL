package com.application.pennypal.domain.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    GOAL_WITHDRAW("GOAL_WITHDRAW"),
    LENDING("LENDING"),
    CHAT("CHAT");
    private final String value;
    NotificationType(String value){
        this.value = value;
    }
}

