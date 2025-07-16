package com.application.pennypal.application.usecases.user;

import com.application.pennypal.application.output.auth.LoginResponseOutput;

public interface LoginUser {
    LoginResponseOutput execute(String email, String password, String ipAddress);
}
