package com.application.pennypal.domain.shared.exception.base;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;

public class DomainBusinessException extends DomainException {
    public DomainBusinessException(String message, DomainErrorCode code) {
        super(message,code);
    }
}