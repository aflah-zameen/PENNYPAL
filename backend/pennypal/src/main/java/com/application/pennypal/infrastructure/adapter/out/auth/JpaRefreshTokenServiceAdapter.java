package com.application.pennypal.infrastructure.adapter.out.auth;

import com.application.pennypal.application.port.out.service.RefreshTokenServicePort;
import com.application.pennypal.infrastructure.config.properties.JwtProperties;
import com.application.pennypal.infrastructure.exception.InfraErrorCode;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.RefreshToken.RefreshTokenRepository;
import com.application.pennypal.infrastructure.persistence.jpa.entity.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaRefreshTokenServiceAdapter implements RefreshTokenServicePort {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;
    @Override
    public void Store(String userId, String token, String ipAddress) {
        RefreshTokenEntity entity = refreshTokenRepository.findByUserIdAndTokenAndIpAddress(userId,token,ipAddress)
                .orElse(null);
        if(entity != null){
            entity = entity.setToken(token);
            entity = entity.setExpiry(LocalDateTime.now().plusSeconds(jwtProperties.refreshExpiration().getSeconds()));
            refreshTokenRepository.save(entity);
        }
        else{
            RefreshTokenEntity newEntity = new RefreshTokenEntity(
                    userId,
                    token,
                    LocalDateTime.now().plusSeconds(jwtProperties.refreshExpiration().getSeconds()),
                    ipAddress
            );
            refreshTokenRepository.save(newEntity);
        }
    }

    @Override
    public Optional<String> loadRefreshToken(String userId,String ipAddress) {
        return refreshTokenRepository.findByUserIdAndIpAddress(userId,ipAddress)
                .map(RefreshTokenEntity::getToken);
    }

    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Override
    public Optional<String> findUserIdByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(RefreshTokenEntity::getUserId);
    }

    @Override
    public Optional<Duration> getTtl(String token) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InfrastructureException("Token not found","NOT_FOUND"));
        return Optional.of(Duration.between(LocalDateTime.now(),refreshToken.getExpiryDate()));
    }
}
