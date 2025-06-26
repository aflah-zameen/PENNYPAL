package com.application.pennypal.application.service;

import com.application.pennypal.application.port.EncodePasswordPort;
import com.application.pennypal.application.usecases.user.CreateUser;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.user.validator.PasswordValidator;
import com.application.pennypal.shared.exception.ApplicationException;
import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.shared.exception.InvalidRoleException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

public class CreateUserService implements CreateUser {
    private final UserRepositoryPort userRepository;
    private final EncodePasswordPort encodePasswordPort;
    private final ValidateEmailUniqueness validateEmailUniqueness;

    public CreateUserService(UserRepositoryPort userRepositoryPort,
                      EncodePasswordPort encodePasswordPort,
                             ValidateEmailUniqueness validateEmailUniqueness){
        this.userRepository =userRepositoryPort;
        this.encodePasswordPort = encodePasswordPort;
        this.validateEmailUniqueness = validateEmailUniqueness;
    }


    @Override
    @Transactional
    public User execute(String name,String email,String password,String phone,String role) {
        Set<Roles> roles = switch (role) {
            case "SUPER_ADMIN" -> Set.of(Roles.SUPER_ADMIN, Roles.ADMIN);
            case "ADMIN" -> Set.of(Roles.ADMIN);
            case "USER" -> Set.of(Roles.USER);
            default -> throw new InvalidRoleException("Invalid role: " + role);
        };
        PasswordValidator.validate(password);
        String encodedPassword = encodePasswordPort.encode(password);
        User user= new User(name,email,encodedPassword,phone,roles);
        validateEmailUniqueness.validate(email);
        return userRepository.save(user);
    }
}
