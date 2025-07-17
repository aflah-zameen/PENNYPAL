package com.application.pennypal.infrastructure.exception.base;

public class DatabaseException extends InfrastructureException {
    public DatabaseException(String message, String infraErrorCode) {
        super(message,infraErrorCode);
    }
}
