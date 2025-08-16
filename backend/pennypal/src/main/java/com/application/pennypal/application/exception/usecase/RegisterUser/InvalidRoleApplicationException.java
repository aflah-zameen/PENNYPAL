package com.application.pennypal.application.exception.usecase.RegisterUser;

import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationValidationException;

public class InvalidRoleApplicationException extends ApplicationValidationException {
    public InvalidRoleApplicationException(String message){
        super(message, ApplicationErrorCode.INVALID_ROLE_EXCEPTION.getErrorCode());
    }
}
