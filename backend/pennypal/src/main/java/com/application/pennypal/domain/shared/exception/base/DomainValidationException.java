package com.application.pennypal.domain.shared.exception.base;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;

public class DomainValidationException extends DomainException{
    public DomainValidationException(String message, DomainErrorCode code){
        super(message,code);
    }
}
