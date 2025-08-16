package com.application.pennypal.domain.user.exception.validation;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;

public class InvalidEmailDomainValidation extends DomainValidationException {
    public InvalidEmailDomainValidation(String message) {

        super(message, DomainErrorCode.INVALID_EMAIL);
    }
}
