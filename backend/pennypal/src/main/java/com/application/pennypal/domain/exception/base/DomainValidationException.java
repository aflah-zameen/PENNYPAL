package com.application.pennypal.domain.exception.base;

import com.application.pennypal.domain.exception.DomainErrorCode;

public class DomainValidationException extends DomainException{
    public DomainValidationException(String message, DomainErrorCode code){
        super(message,code);
    }
}
