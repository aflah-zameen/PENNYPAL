package com.application.pennypal.interfaces.rest.exception.validation;

import com.application.pennypal.interfaces.rest.exception.InterfaceErrorCode;
import com.application.pennypal.interfaces.rest.exception.base.InterfaceException;

public class InvalidRequestException extends InterfaceException {
    public InvalidRequestException(String message,InterfaceErrorCode code) {
        super(message, code);
    }
}
