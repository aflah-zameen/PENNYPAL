package com.application.pennypal.interfaces.rest.dtos.auth;

import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;

public class LoginResponse extends ApiResponse<UserDomainDTO> {
    public LoginResponse(boolean success, UserDomainDTO user, String message){
        super(success,user,message);
    }
}
