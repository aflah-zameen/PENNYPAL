package com.application.pennypal.shared.exception;

public class InvalidCredentialsException extends ApplicationException{
    InvalidCredentialsException(String message){
        super(message,"INVALID_CREDENTIALS");
    }
}
