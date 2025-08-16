package com.application.pennypal.application.service.auth;


import com.application.pennypal.application.port.out.service.RefreshTokenServicePort;
import lombok.RequiredArgsConstructor;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
public class HybridRefreshTokenService {
    private final RefreshTokenServicePort redisPort;
    private final RefreshTokenServicePort dbPort;

    public void save(String userId,String token,String ipAddress){
        dbPort.Store(userId,token,ipAddress);
        redisPort.Store(userId,token,ipAddress);
    }

    public Optional<String> load(String userId,String ipAdress){
        return redisPort.loadRefreshToken(userId,ipAdress)
                .or(() -> dbPort.loadRefreshToken(userId,ipAdress));
    }

    public boolean isValid(String userId,String token,String ipAddress){
        return load(userId,ipAddress).map(stored -> stored.equals(token)).orElse(false);
    }

    public void delete(String token){
        redisPort.deleteRefreshToken(token);
        dbPort.deleteRefreshToken(token);
    }

    public Optional<String> findUserIdByToken(String token){
        return redisPort.findUserIdByToken(token)
                .or(() -> dbPort.findUserIdByToken(token));
    }

    public Optional<Duration> getTtl(String token){
        return redisPort.getTtl(token)
                .or(()->dbPort.getTtl(token));

    }
}
