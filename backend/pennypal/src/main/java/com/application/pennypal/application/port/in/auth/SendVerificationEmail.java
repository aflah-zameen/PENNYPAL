package com.application.pennypal.application.port.in.auth;

public interface SendVerificationEmail {
    void execute(String email);
}
