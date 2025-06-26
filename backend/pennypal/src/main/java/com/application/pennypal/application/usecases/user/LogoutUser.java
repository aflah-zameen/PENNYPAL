package com.application.pennypal.application.usecases.user;

public interface LogoutUser {
    void execute(String refreshToken);
}
