package com.application.pennypal.application.exception.usecase.user;

import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;

public class UserNotFoundApplicationException extends ApplicationBusinessException {
    public UserNotFoundApplicationException(String message) {
        super(message, ApplicationErrorCode.USER_NOT_FOUND.getErrorCode());
    }
}
