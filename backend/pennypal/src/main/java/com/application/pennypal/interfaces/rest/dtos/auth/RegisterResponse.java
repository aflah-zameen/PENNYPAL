package com.application.pennypal.interfaces.rest.dtos.auth;

import com.application.pennypal.application.dto.output.user.RegisterOutputModel;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;

import java.util.List;

public class RegisterResponse extends ApiResponse<RegisterResponseDTO> {

    public RegisterResponse(boolean success,RegisterResponseDTO responseDTO,String message){
        super(success,responseDTO,message);
    }

    public RegisterResponse(boolean success, String message, List<String> errors, String errorCode){
        super(success,message,errors,errorCode);
    }
}
