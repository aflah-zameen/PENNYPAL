package com.application.pennypal.infrastructure.cache.service;

import com.application.pennypal.infrastructure.cache.user.CachedUserDto;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.UserJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.user.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {
    private final SpringDataUserRepository userRepository;

    @Cacheable(value = "user_cache", key = "#email", unless = "#result == null")
    public CachedUserDto findCachedUserDtoByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserJpaMapper::toDomain)
                .map(CachedUserDto::fromDomain)
                .orElse(null);
    }
}
