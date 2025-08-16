package com.application.pennypal.domain.user.exception.validation;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;

public class MissingRoleDomainException extends DomainValidationException {
    public MissingRoleDomainException(String message) {

        super(message, DomainErrorCode.MISSING_ROLE);
    }
}
