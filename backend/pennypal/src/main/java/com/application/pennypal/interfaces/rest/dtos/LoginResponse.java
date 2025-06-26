package com.application.pennypal.interfaces.rest.dtos;

import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.UserDomainDTO;

public class LoginResponse extends ApiResponse<UserDomainDTO>{
    public LoginResponse(boolean success, UserDomainDTO user, String message){
        super(success,user,message);
    }
}
