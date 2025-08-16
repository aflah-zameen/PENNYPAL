package com.application.pennypal.domain.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    GOAL_WITHDRAW("GOAL_WITHDRAW");
    private final String value;
    NotificationType(String value){
        this.value = value;
    }
}

