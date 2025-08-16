package com.application.pennypal.application.exception.usecase.user;

import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;

public class InvalidCredentialsApplicationException extends ApplicationBusinessException {
    public InvalidCredentialsApplicationException(String message)
    {
        super(message, ApplicationErrorCode.INVALID_CREDENTIALS.getErrorCode());
    }
}
