package com.application.pennypal.interfaces.rest.exception.auth;

import com.application.pennypal.interfaces.rest.exception.InterfaceErrorCode;
import com.application.pennypal.interfaces.rest.exception.base.InterfaceException;

public class ForbiddenAccessException extends InterfaceException {
    public ForbiddenAccessException(String message, InterfaceErrorCode code) {
        super(message,code);
    }
}
