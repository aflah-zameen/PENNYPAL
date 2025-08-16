package com.application.pennypal.application.exception.usecase.auth;

import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;

public class EmailNotFoundApplicationException extends ApplicationBusinessException {
    public EmailNotFoundApplicationException(String message) {
        super(message, ApplicationErrorCode.EMAIL_NOT_FOUND.getErrorCode());
    }
}
