package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.usecase.auth.InvalidTokenApplicationException;
import com.application.pennypal.application.port.in.user.LogoutUser;
import com.application.pennypal.application.port.out.service.TokenBlackListPort;
import com.application.pennypal.application.port.out.service.TokenServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
public class LogoutUserService implements LogoutUser {
    private final TokenBlackListPort tokenBlackListPort;
    private final HybridRefreshTokenService hybridRefreshTokenService;
    private final TokenServicePort tokenServicePort;

    @Override
    @Transactional
    public void execute(String refreshToken,String accessToken) {
        if(accessToken != null){
            Duration accessExpiryDate = tokenServicePort.getExpireTime(accessToken);
            tokenBlackListPort.blacklist(accessToken,accessExpiryDate);
        }

        Duration refreshExpiry = hybridRefreshTokenService.getTtl(refreshToken)
                        .orElseThrow(() -> new InvalidTokenApplicationException("Invalid refresh token"));
        tokenBlackListPort.blacklist(refreshToken,refreshExpiry);
        hybridRefreshTokenService.delete(refreshToken);
    }
}
