package com.application.pennypal.infrastructure.security.token;

import com.application.pennypal.infrastructure.exception.InfraErrorCode;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class TokenHashingService {
    private final MessageDigest digest;

    public String hash(String token) {
        try {
            byte[]encoded = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(encoded);
        }catch (Exception e){
            throw new InfrastructureException("Error in token hashing", InfraErrorCode.TOKEN_HASHING_INVALIDATION.code(),e);
        }
    }
}
