package com.application.pennypal.infrastructure.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Getter
@NoArgsConstructor
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    private String ipAddress;

    public RefreshTokenEntity(Long userId, Instant expiryDate, String ipAddress) {
        this.token = UUID.randomUUID().toString();
        this.userId = userId;
        this.expiryDate = expiryDate;
        this.ipAddress = ipAddress;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiryDate);
    }

    public boolean isValidIp(String requestIp) {
        return ipAddress.equals(requestIp);
    }
}