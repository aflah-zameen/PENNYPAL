package com.application.pennypal.application.exception.usecase.auth;

import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;

public class EmailAlreadyVerifiedApplicationException extends ApplicationBusinessException {
    public EmailAlreadyVerifiedApplicationException(String message) {
        super(message, ApplicationErrorCode.EMAIL_ALREADY_VERIFIED.getErrorCode());
    }
}
