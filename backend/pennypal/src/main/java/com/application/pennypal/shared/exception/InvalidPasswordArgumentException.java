package com.application.pennypal.shared.exception;

public class InvalidPasswordArgumentException extends ApplicationException {
    public InvalidPasswordArgumentException(String message) {
        super(message,"PASSWORD_INVALID_ARGUMENT");
    }
}
