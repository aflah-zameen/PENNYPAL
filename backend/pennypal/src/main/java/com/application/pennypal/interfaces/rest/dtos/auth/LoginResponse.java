package com.application.pennypal.interfaces.rest.dtos.auth;

import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;

public class LoginResponse extends ApiResponse<UserResponseDTO> {
    public LoginResponse(boolean success, UserResponseDTO user, String message){
        super(success,user,message);
    }
}
