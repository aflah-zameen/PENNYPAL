package com.application.pennypal.infrastructure.adapter.auth;

import com.application.pennypal.application.port.RefreshTokenServicePort;
import com.application.pennypal.domain.user.valueObject.RefreshTokenInfo;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.RefreshToken.RefreshTokenRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.RefreshTokenEntity;
import com.application.pennypal.infrastructure.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class RefreshTokenServiceAdapter implements RefreshTokenServicePort {
    private final JwtProperties props;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String generateRefreshToken(Long userId, String ipAddress) {
        Instant expiryDate = Instant.now().plusMillis(props.refreshExpiration().toMillis());
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(userId,expiryDate,ipAddress);
        return refreshTokenRepository.save(refreshTokenEntity).getToken();
    }

    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Override
    public Optional<RefreshTokenInfo> findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(entity -> {
                    return new RefreshTokenInfo(
                            entity.getUserId(),
                            entity.getToken(),
                            entity.getExpiryDate(),
                            entity.getIpAddress());
                });
    }

}
