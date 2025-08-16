package com.application.pennypal.domain.user.exception.validation;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;

public class InvalidURLDomainException extends DomainValidationException {
    public InvalidURLDomainException(String message) {
        super(message, DomainErrorCode.INVALID_URL);
    }
}
