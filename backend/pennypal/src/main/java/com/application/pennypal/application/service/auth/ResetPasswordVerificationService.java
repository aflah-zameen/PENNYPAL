package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.exception.usecase.auth.EmailNotFoundApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserInactiveApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserUnverifiedApplicationException;
import com.application.pennypal.application.port.in.auth.ResetPasswordVerification;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.EmailSendPort;
import com.application.pennypal.application.port.out.service.VerificationTokenServicePort;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResetPasswordVerificationService implements ResetPasswordVerification {
    private final UserRepositoryPort userRepositoryPort;
    private final VerificationTokenServicePort verificationTokenServicePort;
    private final EmailSendPort emailSendPort;
    @Override
    public void execute(String email) {
        if(!userRepositoryPort.existsByEmail(email)){
            throw new EmailNotFoundApplicationException("Email is not found");
        }
        User user = userRepositoryPort.findByEmail(email).
            orElseThrow(() -> new UserNotFoundApplicationException("User not found"));

        if(!user.isVerified()){
            throw  new UserUnverifiedApplicationException("User is not verified");
        }

        if(!user.isActive())
        {
            throw new UserInactiveApplicationException("User is blocked");
        }

        /// Generate token and save.
        String token = verificationTokenServicePort.getToken(email);
        verificationTokenServicePort.saveToken(token,email);

        /// Send the token through email
        emailSendPort.sendUserResetPass(user.getName(),email,token);
    }
}
