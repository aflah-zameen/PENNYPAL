package com.application.pennypal.infrastructure.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MessageDigestConfig {
    @Bean
    public MessageDigest sha256Digest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256");
    }
}
