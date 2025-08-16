package com.application.pennypal.infrastructure.adapter.out.auth;

import com.application.pennypal.application.port.out.service.RefreshTokenServicePort;
import com.application.pennypal.infrastructure.config.properties.JwtProperties;
import com.application.pennypal.infrastructure.external.redis.RedisKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisRefreshTokenServiceAdapter implements RefreshTokenServicePort {
    private final RedisKeyUtils redisKeyUtils;
    private final RedisTemplate<String,String> redisTemplate;
    private final JwtProperties jwtProperties;
    @Override
    public void Store(String userId, String token, String ipAddress) {
        String tokenKey = redisKeyUtils.refreshTokenKey(token);
        String tokenUserKey = redisKeyUtils.refreshTokenUserKey(userId);

        /// Reverse Mapping
        redisTemplate.opsForValue().set(tokenUserKey,token,jwtProperties.refreshExpiration());
        redisTemplate.opsForValue().set(tokenKey,userId,jwtProperties.refreshExpiration());
    }

    @Override
    public Optional<String> loadRefreshToken(String userId,String ipAddress) {
        String key = redisKeyUtils.refreshTokenUserKey(userId);
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void deleteRefreshToken(String token) {
        String key = redisKeyUtils.refreshTokenKey(token);
        redisTemplate.delete(key);
    }

    @Override
    public Optional<String> findUserIdByToken(String token) {
        String key = redisKeyUtils.refreshTokenKey(token);
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public Optional<Duration> getTtl(String token) {
        String key = redisKeyUtils.refreshTokenKey(token);
        Long seconds = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if ( seconds == null || seconds < 0) {
            return Optional.empty(); // either key not found or no expiry set
        }
        return Optional.of(Duration.ofSeconds(seconds));
    }
}
