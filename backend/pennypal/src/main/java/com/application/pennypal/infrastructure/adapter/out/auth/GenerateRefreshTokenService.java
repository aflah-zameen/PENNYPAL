package com.application.pennypal.infrastructure.adapter.out.auth;

import com.application.pennypal.application.service.auth.GenerateRefreshTokenPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenerateRefreshTokenService implements GenerateRefreshTokenPort {

    @Override
    public String get() {
        return UUID.randomUUID().toString();
    }
}
