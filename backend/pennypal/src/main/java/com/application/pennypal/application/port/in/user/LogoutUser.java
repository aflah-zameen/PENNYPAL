package com.application.pennypal.application.port.in.user;

public interface LogoutUser {
    void execute(String refreshToken,String accessToken);
}
