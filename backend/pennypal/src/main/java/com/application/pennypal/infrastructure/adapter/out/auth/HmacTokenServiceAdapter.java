package com.application.pennypal.infrastructure.adapter.out.auth;

import com.application.pennypal.application.port.out.service.VerificationTokenServicePort;
import com.application.pennypal.infrastructure.config.properties.AuthProperties;
import com.application.pennypal.infrastructure.external.redis.RedisKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HmacTokenServiceAdapter implements VerificationTokenServicePort {
    private  final RedisTemplate<String,String> redisTemplate;
    private final RedisKeyUtils redisKeyUtils;
    private final AuthProperties authProperties;

    @Override
    public boolean isValid(String token, String email) {
        try {
            // Decode token
            String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = decoded.split("\\$");

            if (parts.length != 3) return false;

            String verificationEmail = parts[0];
            long expiry = Long.parseLong(parts[1]);
            String signature = parts[2];

            if (!verificationEmail.equals(email)) return false;
            if (Instant.now().getEpochSecond() > expiry) return false;

            // Recompute HMAC
            String payload = verificationEmail + "$" + expiry;
            String expectedSig = hmacSha256(payload, authProperties.verificationEmailSecretKey());

            return constantTimeEquals(signature, expectedSig);
        } catch (Exception e) {
            return false; // Fail gracefully
        }
    }

    @Override
    public void saveToken(String token, String email) {
        ValueOperations<String,String> ops = redisTemplate.opsForValue();

        if(ops.get(redisKeyUtils.verificationTokenKey(token)) != null){
            deleteToken(redisKeyUtils.verificationTokenKey(token));
        }
        /// Key is token and email is the value
        ops.set(redisKeyUtils.verificationTokenKey(token),email,authProperties.verificationEmailExpiration() );

        /// Link the email to the latest token (Used to invalid previous token in resend)
        ops.set(redisKeyUtils.latestVerificationTokenKey(email), token, authProperties.verificationEmailExpiration());
    }
    @Override
    public Optional<String> getEmail(String token) {
        ValueOperations<String,String > ops = redisTemplate.opsForValue();
        return Optional.ofNullable(ops.get(redisKeyUtils.verificationTokenKey(token)));
    }

    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(redisKeyUtils.verificationTokenKey(token));
    }

    @Override
    public void deleteUsingEmail(String email) {
        ValueOperations<String,String > ops = redisTemplate.opsForValue();
        String token  = ops.get(redisKeyUtils.latestVerificationTokenKey(email));
        if(token != null)
            deleteToken(token);
        redisTemplate.delete(redisKeyUtils.latestVerificationTokenKey(email));
    }

    @Override
    public String getToken(String email) {
        long expiry = Instant.now().getEpochSecond() + authProperties.verificationEmailExpiration().toSeconds();
        String payload = email+"$"+expiry;

        String signature = hmacSha256(payload, authProperties.verificationEmailSecretKey());
        String token = payload+"$"+signature;

        return  Base64.getUrlEncoder().withoutPadding().encodeToString(token.getBytes(StandardCharsets.UTF_8));
    }
    private String hmacSha256(String data, String key) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secretKey);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC", e);
        }
    }

    private boolean constantTimeEquals(String a, String b) {
        if (a.length() != b.length()) return false;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }


}
