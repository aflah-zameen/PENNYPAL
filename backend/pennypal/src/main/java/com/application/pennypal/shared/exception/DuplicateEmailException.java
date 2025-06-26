package com.application.pennypal.shared.exception;

public class DuplicateEmailException extends ApplicationException{
    public DuplicateEmailException(String message){
        super(message,"DUPLICATE_EMAIL");
    }
}
