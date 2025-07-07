package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.shared.exception.DuplicateEmailException;

import java.util.Optional;

public class ValidateEmailUniqueness {

    private final UserRepositoryPort userRepositoryPort;

    public ValidateEmailUniqueness(UserRepositoryPort userRepositoryPort){
        this.userRepositoryPort = userRepositoryPort;
    }

    public void validate(String email) {
        Optional<User> existingUser = userRepositoryPort.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new DuplicateEmailException("Email already in use");
        }
    }
}
