package com.application.pennypal.interfaces.rest.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private List<String> errors; // for validation errors
    private String errorCode; // Optional, for client debugging

    //success constructor
    public ApiResponse(boolean success,T data,String message){
        this.data = data;
        this.success = success;
        this.message = message;
    }

    //Error constructor
    public ApiResponse(boolean success,String message,List<String> errors,String errorCode){
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.errors = errors;
    }
}
