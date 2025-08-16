package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.exception.usecase.auth.EmailNotFoundApplicationException;
import com.application.pennypal.application.exception.usecase.auth.InvalidTokenApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.in.user.ResetPassword;
import com.application.pennypal.application.port.out.service.VerificationTokenServicePort;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResetPasswordService implements ResetPassword {

    private final UserRepositoryPort userRepositoryPort;
    private final EncodePasswordPort encodePasswordPort;
    private final VerificationTokenServicePort verificationTokenServicePort;

    @Override
    public void reset(String email, String password,String verificationToken) {
        if(!userRepositoryPort.existsByEmail(email))
            throw new EmailNotFoundApplicationException("Email is not found");
        
        if(!verificationTokenServicePort.isValid(verificationToken,email)){
            throw new InvalidTokenApplicationException("Token is not valid");
        }

        /// Validate passwords
        PasswordValidator.validate(password);

        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        String encodedPassword = encodePasswordPort.encode(password);
        user = user.changePassword(encodedPassword);
        userRepositoryPort.update(user);
    }
}
