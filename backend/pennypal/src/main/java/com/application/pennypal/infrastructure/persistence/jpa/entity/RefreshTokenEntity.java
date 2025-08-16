package com.application.pennypal.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Getter
@NoArgsConstructor
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private String ipAddress;

    public RefreshTokenEntity(String userId,String token, LocalDateTime expiryDate, String ipAddress) {
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
        this.ipAddress = ipAddress;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isValidIp(String requestIp) {
        return ipAddress.equals(requestIp);
    }

    public RefreshTokenEntity setToken(String token){
        this.token = token;
        return this;
    }
    public RefreshTokenEntity setExpiry(LocalDateTime expiryDate){
        this.expiryDate = expiryDate;
        return this;
    }
}