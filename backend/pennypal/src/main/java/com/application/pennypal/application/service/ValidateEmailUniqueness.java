package com.application.pennypal.application.service;

import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.shared.exception.DuplicateEmailException;

public class ValidateEmailUniqueness {

    private final UserRepositoryPort userRepositoryPort;

    public ValidateEmailUniqueness(UserRepositoryPort userRepositoryPort){
        this.userRepositoryPort = userRepositoryPort;
    }

    public void validate(String email) {
        if (userRepositoryPort.findByEmail(email).filter(u -> !u.getEmail().equals(email)).isPresent()) {
            throw new DuplicateEmailException("Email already exists");
        }
    }
}
