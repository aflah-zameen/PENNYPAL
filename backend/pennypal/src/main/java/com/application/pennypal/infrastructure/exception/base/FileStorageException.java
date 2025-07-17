package com.application.pennypal.infrastructure.exception.base;

public class FileStorageException extends InfrastructureException {
    public FileStorageException(String message,String infraErrorCode) {
        super(message,infraErrorCode);
    }
}
