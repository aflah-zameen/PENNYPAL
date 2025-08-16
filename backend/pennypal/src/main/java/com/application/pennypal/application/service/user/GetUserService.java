package com.application.pennypal.application.service.user;

import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.out.service.TokenServicePort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserService implements GetUser {
    private final TokenServicePort tokenServicePort;
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public UserDomainDTO get(String accessToken) {
        String email = tokenServicePort.getUsernameFromToken(accessToken);
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        return new UserDomainDTO(user.getUserId(),user.getName(),user.getEmail(),user.getRoles(),
                user.getPhone(), user.isActive(), user.isVerified(),
                user.getCreatedAt(),user.getProfileURL().isPresent() ? user.getProfileURL().get() : null);
    }
}
