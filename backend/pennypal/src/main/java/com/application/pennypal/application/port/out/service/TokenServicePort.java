package com.application.pennypal.application.port.out.service;

import com.application.pennypal.domain.user.entity.User;

import java.time.Duration;
import java.util.Date;
import java.util.Set;

public interface TokenServicePort {
    String generateAccessToken(User user);
    String getUsernameFromToken(String token);
    Set<String> getRolesFromToken(String token);
    Duration getExpireTime(String token);
    boolean isValid(String token);
}
