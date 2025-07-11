package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.port.OtpServicePort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.application.usecases.user.SendOtp;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class SendOtpService implements SendOtp {
    private final UserRepositoryPort userRepositoryPort;
    private final OtpServicePort otpServicePort;
    @Override
    public LocalDateTime send(String email){
//        if(!userRepositoryPort.existsByEmail(email)){
//            throw new UserNotFoundException("User not found");
//        }
        return otpServicePort.generateOtpAndSend(email);
    }

    @Override
    public LocalDateTime resend(String email){
       return otpServicePort.resentOtp(email);
    }
}
