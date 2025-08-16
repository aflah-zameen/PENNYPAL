package com.application.pennypal.application.port.out.service;

import java.time.Duration;
import java.time.LocalDateTime;

public interface OtpServicePort {
    void saveOtp(String email, String otp, Duration expirationMinutes);
    void validateOtp(String email,String otp,String context);
    LocalDateTime resentOtp(String email);
    void sendOtp(String name,String email,String otp,int expirationMinutes);
}
