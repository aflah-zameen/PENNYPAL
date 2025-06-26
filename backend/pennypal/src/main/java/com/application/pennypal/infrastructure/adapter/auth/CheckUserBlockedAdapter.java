package com.application.pennypal.infrastructure.adapter.auth;

import com.application.pennypal.application.port.CheckUserBlockedPort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckUserBlockedAdapter implements CheckUserBlockedPort {
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public boolean check(String email) {
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return !user.isActive();
    }
}
