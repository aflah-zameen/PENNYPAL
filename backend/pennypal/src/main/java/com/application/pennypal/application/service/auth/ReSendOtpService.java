package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.EmailSendPort;
import com.application.pennypal.application.port.out.service.OtpCachePort;
import com.application.pennypal.application.port.in.user.ReSendOtp;
import com.application.pennypal.application.port.out.service.OtpGeneratorPort;
import com.application.pennypal.domain.user.entity.User;


public class ReSendOtpService implements ReSendOtp {
    private final OtpCachePort otpCachePort;
    private final EmailSendPort emailSendPort;
    private final UserRepositoryPort userRepositoryPort;
    private final OtpGeneratorPort otpGeneratorPort;

    public ReSendOtpService(OtpCachePort otpCachePort,EmailSendPort emailSendPort,UserRepositoryPort userRepositoryPort,OtpGeneratorPort otpGeneratorPort){
        this.otpCachePort = otpCachePort;
        this.emailSendPort = emailSendPort;
        this.userRepositoryPort = userRepositoryPort;
        this.otpGeneratorPort = otpGeneratorPort;
    }

    @Override
    public void send(String email){
        User user = userRepositoryPort.findByEmail(email)
                        .orElseThrow(()->new UserNotFoundApplicationException("User is not registered"));

        /// Delete the previous otp
        otpCachePort.deleteOtp(email);

        /// Generate otp
        String otp = otpGeneratorPort.generate();

        /// Save new otp
        otpCachePort.saveOtp(email,otp);

        /// Send email
        emailSendPort.sendUserVerificationOtp(user.getEmail(), user.getName(), otp);

    }

}
