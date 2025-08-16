package com.application.pennypal.infrastructure.external.redis;

import com.application.pennypal.infrastructure.external.redis.config.RedisKeyPrefixProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisKeyUtils {
    private final RedisKeyPrefixProperties prefix;

    public String otpKey(String email){
        return prefix.getOtp()+":"+email;
    }
    public String  verificationTokenKey(String token){
        return prefix.getVerificationToken()+":"+token;
    }

    public String latestVerificationTokenKey(String email){
        return prefix.getLatestVerificationToken()+":"+email;
    }

    public String jwtBlackListKey(String hashedToken){
        return prefix.getJwtBlacklist()+":"+hashedToken;
    }

    public String refreshTokenUserKey(String userId){
        return prefix.getRefreshTokenUser()+":"+userId;
    }
    public String refreshTokenKey(String token){
        return prefix.getRefreshToken()+":"+token;
    }
}
