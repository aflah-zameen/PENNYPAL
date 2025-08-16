package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.out.service.TokenServicePort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.in.user.RefreshAccessToken;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.valueObject.TokenPairDTO;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.interfaces.rest.exception.auth.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefreshAccessTokenService implements RefreshAccessToken {

    private final HybridRefreshTokenService hybridRefreshTokenService;
    private final TokenServicePort tokenServicePort;
    private final GenerateRefreshTokenPort generateRefreshTokenPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public TokenPairDTO execute(String refreshToken, String ipAddress) {
        String userId = hybridRefreshTokenService.findUserIdByToken(refreshToken)
                .orElseThrow(()->new ApplicationBusinessException("User in the refresh token is invalid.","TOKEN_INVALID"));
        if(!hybridRefreshTokenService.isValid(userId,refreshToken,ipAddress)){
            throw new UnauthorizedAccessException("Refresh token doesn't match");
        }
        String newRefreshToken = generateRefreshTokenPort.get();
        User user = userRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new InfrastructureException("User not found","NOT_FOUND"));
        String newAccessToken =  tokenServicePort.generateAccessToken(user);

        hybridRefreshTokenService.save(userId,newRefreshToken,ipAddress);
        return new TokenPairDTO(newRefreshToken,newAccessToken);
    }
}
