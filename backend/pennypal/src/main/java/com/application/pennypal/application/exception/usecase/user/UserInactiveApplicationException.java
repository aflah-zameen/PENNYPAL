package com.application.pennypal.application.exception.usecase.user;

import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;

public class UserInactiveApplicationException extends ApplicationBusinessException {
    public UserInactiveApplicationException(String message) {
        super(message, ApplicationErrorCode.USER_INACTIVE.getErrorCode());
    }
}
