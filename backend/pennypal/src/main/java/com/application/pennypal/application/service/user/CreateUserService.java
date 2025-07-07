package com.application.pennypal.application.service.user;

import com.application.pennypal.application.port.EncodePasswordPort;
import com.application.pennypal.application.port.S3SystemPort;
import com.application.pennypal.application.service.auth.ValidateEmailUniqueness;
import com.application.pennypal.application.usecases.user.CreateUser;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.user.validator.PasswordValidator;
import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.shared.exception.InvalidRoleException;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class CreateUserService implements CreateUser {
    private final UserRepositoryPort userRepository;
    private final EncodePasswordPort encodePasswordPort;
    private final ValidateEmailUniqueness validateEmailUniqueness;
    private final S3SystemPort systemPort;

    public CreateUserService(UserRepositoryPort userRepositoryPort,
                      EncodePasswordPort encodePasswordPort,
                             ValidateEmailUniqueness validateEmailUniqueness,
                             S3SystemPort  systemPort){
        this.userRepository =userRepositoryPort;
        this.encodePasswordPort = encodePasswordPort;
        this.validateEmailUniqueness = validateEmailUniqueness;
        this.systemPort =systemPort;
    }


    @Override
    @Transactional
    public User execute(String name, String email, String password, String phone, String role, MultipartFile profileImageFile) {
        Set<Roles> roles = switch (role) {
            case "SUPER_ADMIN" -> Set.of(Roles.SUPER_ADMIN, Roles.ADMIN);
            case "ADMIN" -> Set.of(Roles.ADMIN);
            case "USER" -> Set.of(Roles.USER);
            default -> throw new InvalidRoleException("Invalid role: " + role);
        };
        PasswordValidator.validate(password);
        String encodedPassword = encodePasswordPort.encode(password);
        String profileURL = systemPort.uploadFile(profileImageFile);
        User user= new User(name,email,encodedPassword,phone,roles,profileURL);
        validateEmailUniqueness.validate(email);
        return userRepository.save(user);
    }
}
