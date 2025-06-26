package com.application.pennypal.infrastructure.adapter.persistence.jpa.RefreshToken;

import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUserId(Long userId);
    void deleteByToken(String token);
    @Query("SELECT rt.userId FROM RefreshTokenEntity rt WHERE rt.token = :token AND rt.ipAddress = :ipAddress AND rt.expiryDate > :now")
    Optional<Long>  findUserIdByToken(@Param("token")String token, @Param("ipAddress")  String ipAddress, @Param("now") Instant now);
}
