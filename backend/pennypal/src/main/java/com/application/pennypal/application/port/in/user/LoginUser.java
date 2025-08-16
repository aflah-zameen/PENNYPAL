package com.application.pennypal.application.port.in.user;

import com.application.pennypal.application.dto.output.auth.LoginResponseOutput;

public interface LoginUser {
    LoginResponseOutput execute(String email, String password, String ipAddress);
}
