package com.application.pennypal.application.port.out.service;

public interface EmailSendPort {
    void sendUserVerificationOtp(String email,String userName, String otpCode);
    void sendAdminVerification(String name,String email,String verificationToken);
    void sendUserResetPass(String name,String email,String verificationToken);
}
