package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.exception.usecase.auth.InvalidTokenApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.in.auth.VerifyEmailWithToken;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.VerificationTokenServicePort;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VerifyEmailWithTokenService implements VerifyEmailWithToken {
    private final VerificationTokenServicePort verificationTokenServicePort;
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public void execute(String token) {
        String email  = verificationTokenServicePort.getEmail(token)
                .orElseThrow(()-> new InvalidTokenApplicationException("Token is invalid or expired"));
        if(!verificationTokenServicePort.isValid(token,email)){
            throw new InvalidTokenApplicationException("Invalid token!.");
        }

        User admin = userRepositoryPort.findByEmail(email).orElseThrow(
                () -> new UserNotFoundApplicationException("Admin is not found")
        );
        User verifiedAdmin = admin.verify();
        userRepositoryPort.update(verifiedAdmin);
        verificationTokenServicePort.deleteToken(token);
    }
}
