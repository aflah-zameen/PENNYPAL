package com.application.pennypal.domain.user.exception.validation;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;

public class MissingNameDomainException extends DomainValidationException {
    public MissingNameDomainException(String message)
    {
        super(message, DomainErrorCode.MISSING_NAME);
    }
}
