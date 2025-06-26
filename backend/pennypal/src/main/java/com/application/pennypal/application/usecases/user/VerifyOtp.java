package com.application.pennypal.application.usecases.user;

public interface VerifyOtp {
    void verify(String email,String otp,String context);
}
