package com.application.pennypal.application.port.in.auth;

public interface ResetPasswordVerification {
    void execute(String email);
}
