package com.application.pennypal.application.service;

import com.application.pennypal.application.usecases.user.LogoutUser;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.RefreshToken.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogoutUserService implements LogoutUser {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void execute(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
