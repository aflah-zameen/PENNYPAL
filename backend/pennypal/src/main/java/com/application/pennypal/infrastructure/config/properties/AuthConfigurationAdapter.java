package com.application.pennypal.infrastructure.config.properties;

import com.application.pennypal.application.port.out.config.AuthConfigurationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
@Component
@RequiredArgsConstructor
public class AuthConfigurationAdapter implements AuthConfigurationPort {
    private final AuthProperties authProperties;
    @Override
    public Duration getOtpExpiration() {
        return authProperties.otpExpiration();
    }

    @Override
    public Duration getVerificationEmailExpiration() {
        return authProperties.verificationEmailExpiration();
    }
}
