package com.application.pennypal.infrastructure.security.token;

import com.application.pennypal.application.port.out.service.TokenBlackListPort;
import com.application.pennypal.infrastructure.external.redis.RedisKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisTokenBlacklistAdapter implements TokenBlackListPort {

    private final RedisTemplate<String,String> redisTemplate;
    private final TokenHashingService tokenHashingService;
    private final RedisKeyUtils redisKeyUtils;

    @Override
    public void blacklist(String token, Duration expiry) {
            String key = redisKeyUtils.jwtBlackListKey(tokenHashingService.hash(token));
            redisTemplate.opsForValue().set(key,"true",expiry);
    }

    @Override
    public boolean isBlacklisted(String token) {
        String key = redisKeyUtils.jwtBlackListKey(tokenHashingService.hash(token));
        return redisTemplate.hasKey(key);
    }
}
