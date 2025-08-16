package com.application.pennypal.domain.user.entity;

import com.application.pennypal.domain.user.exception.validation.InvalidTokenExpiryDomainException;
import com.application.pennypal.domain.valueObject.TokenPurpose;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class VerificationToken {
    private final String token;
    private final String userId;
    private final LocalDateTime expiryTime;
    private final boolean used = false;
    private final TokenPurpose purpose;
    private final LocalDateTime createdAt;
    private final LocalDateTime usedAt;

    private VerificationToken(String token,String userId,LocalDateTime expiryTime,TokenPurpose purpose,LocalDateTime createdAt,LocalDateTime usedAt){
        this.token = token;
        this.userId = userId;
        this.expiryTime = expiryTime;
        this.purpose = purpose;
        this.createdAt = createdAt;
        this.usedAt = usedAt;
    }

    public VerificationToken create(String token,String userId,LocalDateTime expiryTime,TokenPurpose purpose){
        if(expiryTime.isBefore(LocalDateTime.now())){
            throw new InvalidTokenExpiryDomainException("Expiry time must not be in the past");
        }
        return new VerificationToken(
                token,
                userId,
                expiryTime,
                purpose,
                null,
                null);
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expiryTime);
    }


}
