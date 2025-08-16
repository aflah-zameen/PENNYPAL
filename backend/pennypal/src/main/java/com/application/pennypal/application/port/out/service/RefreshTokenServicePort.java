package com.application.pennypal.application.port.out.service;

import java.time.Duration;
import java.util.Optional;

public interface RefreshTokenServicePort {
    void Store(String userId,String token,String ipAddress);
    Optional<String> loadRefreshToken (String userId,String ipAddress);
    void deleteRefreshToken(String token);
    Optional<String> findUserIdByToken(String token);
    Optional<Duration> getTtl(String token);
}
