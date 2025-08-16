package com.application.pennypal.application.exception.usecase.auth;

import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;

public class InvalidOtpApplicationException extends ApplicationBusinessException {
    public InvalidOtpApplicationException(String message) {
        super(message, ApplicationErrorCode.INVALID_OTP_EXCEPTION.getErrorCode());
    }
}
