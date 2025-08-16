package com.application.pennypal.infrastructure.adapter.out.auth;

import com.application.pennypal.application.port.out.service.CheckUserBlockedPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckUserBlockedAdapter implements CheckUserBlockedPort {
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public boolean check(String email) {
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new InfrastructureException("User not found","NOT_FOUND"));
        return !user.isActive();
    }
}
