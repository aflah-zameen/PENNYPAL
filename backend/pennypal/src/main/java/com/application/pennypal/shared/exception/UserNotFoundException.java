package com.application.pennypal.shared.exception;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String message) {
        super(message,"USER_NOT_FOUND");
    }
}
