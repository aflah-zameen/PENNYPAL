package com.application.pennypal.infrastructure.external.websocket.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;
@AllArgsConstructor
public class AuthenticatedUser implements Principal {
    private final String userId;
    @Getter
    private final String email;
    private final String name;
    @Override
    public String getName() {
        return this.userId;
    }
    public String getUsername(){
        return this.name;
    }
}
