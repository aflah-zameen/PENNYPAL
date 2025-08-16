package com.application.pennypal.infrastructure.adapter.out.utils;

import com.application.pennypal.application.port.out.service.OtpGeneratorPort;
import com.application.pennypal.infrastructure.config.properties.AuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class OtpGeneratorAdapter implements OtpGeneratorPort {
    private final AuthProperties properties;
    @Override
    public String generate() {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for(int i=0;i< properties.otpLength();i++){
            builder.append(random.nextInt(10));
        }
        System.out.println("Generated OTP: " + builder.toString());
        return builder.toString();
    }
}
