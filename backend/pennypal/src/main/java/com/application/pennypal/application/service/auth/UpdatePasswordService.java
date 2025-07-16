package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.port.EncodePasswordPort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.application.usecases.user.UpdatePassword;
import com.application.pennypal.domain.entity.User;
import com.application.pennypal.domain.validator.PasswordValidator;
import com.application.pennypal.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdatePasswordService implements UpdatePassword {

    private final UserRepositoryPort userRepositoryPort;
    private final EncodePasswordPort encodePasswordPort;

    @Override
    public void update(String email, String password) {
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        PasswordValidator.validate(password);
        String encodedPassword = encodePasswordPort.encode(password);
        user.setPassword(encodedPassword);
        userRepositoryPort.save(user);
    }
}
