package com.application.pennypal.application.port;

import com.application.pennypal.domain.valueObject.RefreshTokenInfo;

import java.util.Optional;

public interface RefreshTokenServicePort {
    String generateRefreshToken(Long userId,String ipAddress);
    void deleteRefreshToken(String token);
    Optional<RefreshTokenInfo>  findByToken(String token);
}
