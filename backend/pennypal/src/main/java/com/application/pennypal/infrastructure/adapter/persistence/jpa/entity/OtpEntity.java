package com.application.pennypal.infrastructure.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
@Getter
@Setter
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean used;

    public OtpEntity() {}

    public OtpEntity(String email, String otp, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.email = email;
        this.otp = otp;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.used = false;
    }
}
