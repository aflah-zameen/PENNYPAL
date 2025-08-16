package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.exception.usecase.auth.EmailAlreadyVerifiedApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.in.auth.SendVerificationEmail;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.EmailSendPort;
import com.application.pennypal.application.port.out.service.VerificationTokenServicePort;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendVerificationEmailService implements SendVerificationEmail {
    private final UserRepositoryPort userRepositoryPort;
    private final EmailSendPort emailSendPort;
    private final VerificationTokenServicePort verificationTokenServicePort;

    @Override
    public void execute(String email) {
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        if(user.isVerified())
            throw new EmailAlreadyVerifiedApplicationException("User is already verified");

        /// Clear previous Token in cache
        verificationTokenServicePort.deleteUsingEmail(email);

        /// Generate verification token
        String verificationToken = verificationTokenServicePort.getToken(email);


        /// Send Verification Email
        emailSendPort.sendAdminVerification(user.getName(), user.getEmail(),verificationToken);
    }
}
