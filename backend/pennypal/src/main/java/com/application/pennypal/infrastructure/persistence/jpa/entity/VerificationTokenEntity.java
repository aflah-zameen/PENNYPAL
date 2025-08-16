package com.application.pennypal.infrastructure.persistence.jpa.entity;

import com.application.pennypal.domain.valueObject.TokenPurpose;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "verification_tokens")
public class VerificationTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private final String token;

    @Column(nullable = false)
    private final LocalDateTime expiryTime;

    @Column(nullable = false)
    private boolean used = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final TokenPurpose purpose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private final UserEntity user;

    @Column(nullable = false, updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime usedAt;


    /// constructor
    public VerificationTokenEntity(String token,LocalDateTime expiryTime,TokenPurpose tokenPurpose,UserEntity user){
        this.token = token;
        this.expiryTime = expiryTime;
        this.purpose = tokenPurpose;
        this.user = user;
    }

    /// Setters
    public VerificationTokenEntity makeUsed(){
        this.used =  true;
        this.usedAt = LocalDateTime.now();
        return this;
    }



}