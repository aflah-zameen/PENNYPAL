package com.application.pennypal.application.exception.base;

public class ApplicationBusinessException extends ApplicationException {
    private final String code;
    public ApplicationBusinessException(String message, String code) {
        super(message);
        this.code = code;
    }
    public String getErrorCode(){
      return code;
    }
}
