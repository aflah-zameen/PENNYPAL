package com.application.pennypal.domain.user.event;

import lombok.Getter;

import java.time.Duration;
import java.util.UUID;

@Getter
public class AdminRegisteredEvent {
    private final String userId;
    private final String email;
    private final String name;

    public AdminRegisteredEvent(String userId, String email, String name){
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
}
