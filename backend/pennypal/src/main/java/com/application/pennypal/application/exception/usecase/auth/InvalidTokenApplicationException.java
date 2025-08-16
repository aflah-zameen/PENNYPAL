package com.application.pennypal.application.exception.usecase.auth;

import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.domain.shared.exception.DomainErrorCode;

public class InvalidTokenApplicationException extends ApplicationBusinessException {
    public InvalidTokenApplicationException(String message) {
        super(message, ApplicationErrorCode.INVALID_TOKEN_EXCEPTION.getErrorCode());
    }
}
