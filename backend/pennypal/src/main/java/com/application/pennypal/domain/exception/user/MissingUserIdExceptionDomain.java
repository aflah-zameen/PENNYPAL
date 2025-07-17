package com.application.pennypal.domain.exception.user;

import com.application.pennypal.domain.exception.DomainErrorCode;
import com.application.pennypal.domain.exception.base.DomainValidationException;

public class MissingUserIdExceptionDomain extends DomainValidationException {
    public MissingUserIdExceptionDomain(){
        super("User ID must not be null",DomainErrorCode.MISSING_USER_ID);
    }

}
