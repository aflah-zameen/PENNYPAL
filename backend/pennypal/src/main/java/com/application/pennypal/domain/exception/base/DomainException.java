package com.application.pennypal.domain.exception.base;

import com.application.pennypal.domain.exception.DomainErrorCode;

public class DomainException extends RuntimeException {
    private final DomainErrorCode code;
    public DomainException(String message,DomainErrorCode domainErrorCode) {
        super(message);
        this.code= domainErrorCode;
    }
    public DomainErrorCode code(){
        return code;
    }
}
