package com.application.pennypal.infrastructure.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Configuration
public class JwtConfig {
    @Bean
    @Scope("singleton")
    public KeyPair keyPairConfig() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator =KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
