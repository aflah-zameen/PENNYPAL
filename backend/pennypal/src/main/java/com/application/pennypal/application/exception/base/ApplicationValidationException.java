package com.application.pennypal.application.exception.base;

public class ApplicationValidationException extends ApplicationException {
    private final String code;
    public ApplicationValidationException(String message,String code) {
        super(message);
        this.code = code;
    }
    public String getErrorCode(){
        return code;
    }
}
