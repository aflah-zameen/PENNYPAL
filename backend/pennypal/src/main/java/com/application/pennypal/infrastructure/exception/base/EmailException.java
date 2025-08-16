package com.application.pennypal.infrastructure.exception.base;

import com.application.pennypal.infrastructure.exception.InfraErrorCode;

public class EmailException extends InfrastructureException {
    public EmailException(String message,InfraErrorCode code) {
        super(message, code.code());
    }
}
