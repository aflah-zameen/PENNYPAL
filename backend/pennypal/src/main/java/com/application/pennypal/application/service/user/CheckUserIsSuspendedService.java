package com.application.pennypal.application.service.user;

import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.in.user.CheckUserIsSuspended;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckUserIsSuspendedService implements CheckUserIsSuspended {
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public boolean execute(String email) {
        User user =  userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        return user.isSuspended();
    }
}
