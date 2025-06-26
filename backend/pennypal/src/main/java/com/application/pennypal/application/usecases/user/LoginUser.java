package com.application.pennypal.application.usecases.user;

import com.application.pennypal.application.dto.LoginResponseDTO;

public interface LoginUser {
    LoginResponseDTO execute(String email,String password,String ipAddress);
}
