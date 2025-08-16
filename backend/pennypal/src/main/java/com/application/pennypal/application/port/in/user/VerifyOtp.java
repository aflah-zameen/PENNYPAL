package com.application.pennypal.application.port.in.user;

public interface VerifyOtp {
    void verify(String email,String otp);
}
