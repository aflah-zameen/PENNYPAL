package com.application.pennypal.domain.user.exception.validation;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;

public class MissingUserIdDomainException extends DomainValidationException {
    public MissingUserIdDomainException(){
        super("User ID must not be null",DomainErrorCode.MISSING_USER_ID);
    }

}
