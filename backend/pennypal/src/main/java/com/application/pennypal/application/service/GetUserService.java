package com.application.pennypal.application.service;

import com.application.pennypal.application.port.TokenServicePort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.application.usecases.user.GetUser;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.UserDomainDTO;
import com.application.pennypal.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserService implements GetUser {
    private final TokenServicePort tokenServicePort;
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public UserDomainDTO get(String accessToken) {
        String email = tokenServicePort.getUsernameFromToken(accessToken);
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserDomainDTO(user.getId(),user.getName(),user.getEmail(),user.getRoles(),
                user.getPhone(), user.isActive(), user.isVerified(),
                user.getCreatedAt(),user.getUpdatedAt());
    }
}
