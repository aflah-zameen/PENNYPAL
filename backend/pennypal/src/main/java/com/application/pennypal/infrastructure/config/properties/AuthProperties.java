package com.application.pennypal.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "auth")
public record AuthProperties(
        Integer otpLength,
        Duration otpExpiration,
        String otpSender,
        String VerificationEmailUrl,
        String verificationEmailSecretKey,
        Duration verificationEmailExpiration

                             ) {
}
