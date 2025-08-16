package com.application.pennypal.infrastructure.exception.fileStorage;

import com.application.pennypal.infrastructure.exception.InfraErrorCode;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;

public class EmptyFileInfrastructureException extends InfrastructureException {
    public EmptyFileInfrastructureException(String message) {
        super(message, InfraErrorCode.FILE_STORAGE_FAILURE.code());
    }
}
