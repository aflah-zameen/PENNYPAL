package com.application.pennypal.application.port.out.service;

import java.util.Optional;

public interface OtpCachePort {
    void saveOtp(String email, String otp);
    Optional<String> getOtp(String email);
    void deleteOtp(String email);
}
