package com.application.pennypal.domain.chat;

import lombok.Getter;

@Getter
public enum MessageStatus {
    SEND("SEND"),
    DELIVERED("DELIVERED"),
    DELETED("DELETED"),
    READ("READ");

    private final String value;
    MessageStatus(String value){
        this.value = value;
    }
}
