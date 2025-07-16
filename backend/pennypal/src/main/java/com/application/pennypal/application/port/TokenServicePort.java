package com.application.pennypal.application.port;

import com.application.pennypal.domain.entity.User;

import java.util.Set;

public interface TokenServicePort {
    String generateAccessToken(User user);
    String getUsernameFromToken(String token);
    Set<String> getRolesFromToken(String token);
}
