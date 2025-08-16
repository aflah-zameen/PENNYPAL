package com.application.pennypal.infrastructure.adapter.out.auth;

import com.application.pennypal.application.port.out.service.OtpCachePort;
import com.application.pennypal.infrastructure.config.properties.AuthProperties;
import com.application.pennypal.infrastructure.external.redis.RedisKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class RedisOtpCacheAdapter implements OtpCachePort {

    private  final RedisTemplate<String,String> redisTemplate;
    private final RedisKeyUtils redisKeyUtils;
    private final AuthProperties authProperties;

    /// Save otp
    @Override
    public void saveOtp(String email, String otp) {
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        ops.set(redisKeyUtils.otpKey(email),otp,authProperties.otpExpiration());
    }

    @Override
    public Optional<String> getOtp(String email) {
        ValueOperations<String,String > ops = redisTemplate.opsForValue();
        return Optional.ofNullable(ops.get(redisKeyUtils.otpKey(email)));
    }

    @Override
    public void deleteOtp(String email) {
        redisTemplate.delete(redisKeyUtils.otpKey(email));
    }
}
