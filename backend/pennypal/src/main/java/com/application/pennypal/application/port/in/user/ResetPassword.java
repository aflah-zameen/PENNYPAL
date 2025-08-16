package com.application.pennypal.application.port.in.user;

public interface ResetPassword {
    void reset(String email, String password,String verificationToken);
}
