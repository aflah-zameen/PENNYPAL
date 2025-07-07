package com.application.pennypal.shared.exception;

public class DuplicateException extends ApplicationException {
    public DuplicateException(String message) {
        super(message,"DUPLICATE_CATEGORY");
    }
}
