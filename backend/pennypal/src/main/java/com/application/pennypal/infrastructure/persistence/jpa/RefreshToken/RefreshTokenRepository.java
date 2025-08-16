package com.application.pennypal.infrastructure.persistence.jpa.RefreshToken;

import com.application.pennypal.infrastructure.persistence.jpa.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUserId(String userId);
    void deleteByToken(String token);

    Optional<RefreshTokenEntity> findByUserIdAndTokenAndIpAddress(String userId, String token, String ipAddress);

    Optional<RefreshTokenEntity> findByUserIdAndIpAddress(String userId, String ipAddress);
}
