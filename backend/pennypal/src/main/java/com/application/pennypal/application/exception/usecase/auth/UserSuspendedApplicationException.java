package com.application.pennypal.application.exception.usecase.auth;

import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;

public class UserSuspendedApplicationException extends ApplicationBusinessException {
    public UserSuspendedApplicationException(String message) {
        super(message, ApplicationErrorCode.USER_SUSPENDED.getErrorCode());
    }
}
