package com.application.pennypal.shared.exception;

public class MissingRefreshTokenException extends ApplicationException{
    public MissingRefreshTokenException(String message){
        super(message,"REFRESH_TOKEN_EMPTY");
    }
}
