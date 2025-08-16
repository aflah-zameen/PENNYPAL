package com.application.pennypal.application.port.in.auth;

public interface VerifyEmailWithToken {
    void execute(String token);
}
