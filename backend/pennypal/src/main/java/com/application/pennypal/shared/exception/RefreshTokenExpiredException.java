package com.application.pennypal.shared.exception;

public class RefreshTokenExpiredException extends ApplicationException {
    public RefreshTokenExpiredException(String message) {
        super(message,"REFRESH_TOKEN_EXPIRED");
    }
}
