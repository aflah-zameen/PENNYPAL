package com.application.pennypal.infrastructure.external.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis.prefixes")
@Getter
@Setter
public class RedisKeyPrefixProperties {
    private String otp;
    private String verificationToken;
    private String latestVerificationToken;
    private String jwtBlacklist;
    private String refreshToken;
    private String refreshTokenUser;
}
