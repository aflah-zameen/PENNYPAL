package com.application.pennypal.domain.user.exception.validation;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;

public class MissingProfileURLDomainException extends DomainValidationException {
    public MissingProfileURLDomainException(String message) {
        super(message, DomainErrorCode.MISSING_PROFILE_URL);
    }
}
