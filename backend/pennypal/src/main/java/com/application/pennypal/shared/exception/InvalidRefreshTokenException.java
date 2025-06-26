package com.application.pennypal.shared.exception;

public class InvalidRefreshTokenException extends ApplicationException {
    public InvalidRefreshTokenException(String message) {
        super(message,"INVALID_REFRESH_TOKEN");
    }
}
