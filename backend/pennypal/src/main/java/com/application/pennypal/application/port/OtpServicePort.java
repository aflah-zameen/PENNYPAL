package com.application.pennypal.application.port;

import java.time.Instant;
import java.time.LocalDateTime;

public interface OtpServicePort {
    LocalDateTime generateOtpAndSend(String email);
    void validateOtp(String email,String otp,String context);
    LocalDateTime resentOtp(String email);
}
