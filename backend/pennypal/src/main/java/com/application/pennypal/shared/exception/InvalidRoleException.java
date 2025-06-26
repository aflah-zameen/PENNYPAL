package com.application.pennypal.shared.exception;

public class InvalidRoleException extends ApplicationException {
    public InvalidRoleException(String message) {
        super(message,"INVALID_ROLE");
    }
}
