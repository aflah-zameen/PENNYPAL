package com.application.pennypal.interfaces.rest.exception.auth;

import com.application.pennypal.interfaces.rest.exception.InterfaceErrorCode;
import com.application.pennypal.interfaces.rest.exception.base.InterfaceException;

public class TokenNotFoundInterfaceException extends InterfaceException {
    public TokenNotFoundInterfaceException(String message) {
        super(message, InterfaceErrorCode.MISSING_REQUIRED_FIELD);
    }
}
