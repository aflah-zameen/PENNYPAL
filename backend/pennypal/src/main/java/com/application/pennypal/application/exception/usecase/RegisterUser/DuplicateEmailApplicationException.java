package com.application.pennypal.application.exception.usecase.RegisterUser;

import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;


public class DuplicateEmailApplicationException extends ApplicationBusinessException {
    public DuplicateEmailApplicationException(String message) {
        super(message, ApplicationErrorCode.DUPLICATE_EMAIL.getErrorCode());
    }
}
