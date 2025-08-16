package com.application.pennypal.domain.user.exception.validation;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;

public class InvalidTokenExpiryDomainException extends DomainValidationException {
    public InvalidTokenExpiryDomainException(String message) {
        super(message, DomainErrorCode.INVALID_TOKEN_EXPIRY);
    }
}
