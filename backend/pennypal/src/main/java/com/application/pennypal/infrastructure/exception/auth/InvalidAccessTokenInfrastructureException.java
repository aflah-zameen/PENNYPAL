package com.application.pennypal.infrastructure.exception.auth;

import com.application.pennypal.infrastructure.exception.InfraErrorCode;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;

public class InvalidAccessTokenInfrastructureException extends InfrastructureException {
    public InvalidAccessTokenInfrastructureException(String message) {
        super(message, InfraErrorCode.INVALID_ACCESS_TOKEN.code());
    }
}
