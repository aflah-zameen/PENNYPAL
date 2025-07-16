package com.application.pennypal.interfaces.rest.dtos.auth;

import com.application.pennypal.interfaces.rest.dtos.ApiResponse;

import java.util.List;

public class RegisterResponse extends ApiResponse<String> {

    public RegisterResponse(boolean success,String message){
        super(success,null,message);
    }

    public RegisterResponse(boolean success, String message, List<String> errors, String errorCode){
        super(success,message,errors,errorCode);
    }
}
