package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.port.OtpServicePort;
import com.application.pennypal.application.usecases.user.VerifyOtp;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VerifyOtpService implements VerifyOtp {
    private final OtpServicePort otpServicePort;
    @Override
    public void verify(String email, String otp,String context) {
        otpServicePort.validateOtp(email,otp,context);
    }
}
