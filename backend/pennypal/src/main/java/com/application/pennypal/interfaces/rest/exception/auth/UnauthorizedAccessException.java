package com.application.pennypal.interfaces.rest.exception.auth;

import com.application.pennypal.interfaces.rest.exception.base.InterfaceException;

public class UnauthorizedAccessException extends InterfaceException {
    public UnauthorizedAccessException(String message) {
        super(message,"UNAUTHORIZED");
    }
}
