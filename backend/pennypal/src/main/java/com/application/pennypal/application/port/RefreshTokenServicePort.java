package com.application.pennypal.application.port;

import com.application.pennypal.domain.user.valueObject.RefreshTokenInfo;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenServicePort {
    String generateRefreshToken(Long userId,String ipAddress);
    void deleteRefreshToken(String token);
    Optional<RefreshTokenInfo>  findByToken(String token);
}
