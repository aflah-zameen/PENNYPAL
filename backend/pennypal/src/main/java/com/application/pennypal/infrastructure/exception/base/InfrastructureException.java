package com.application.pennypal.infrastructure.exception.base;

import com.application.pennypal.infrastructure.exception.InfraErrorCode;

public class InfrastructureException extends RuntimeException {
    private final String code;
    public InfrastructureException(String message,String infraErrorCode) {
        super(message);
        this.code = infraErrorCode;
    }
    public InfrastructureException(String message,String infraErrorCode,Throwable cause) {
        super(message,cause);
        this.code = infraErrorCode;
    }

    public String getErrorCode(){
        return code;
    }
}
