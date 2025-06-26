package com.application.pennypal.application.port;

import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.TokenPairDTO;
import com.application.pennypal.domain.user.valueObject.Roles;

import java.util.Set;

public interface TokenServicePort {
    String generateAccessToken(User user);
    String getUsernameFromToken(String token);
    Set<String> getRolesFromToken(String token);
}
