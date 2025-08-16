package com.application.pennypal.domain.shared.exception;

import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class MissingCreatedAtExceptionDomain extends DomainBusinessException {
    public MissingCreatedAtExceptionDomain(){
        super("Created timestamp must not be null",DomainErrorCode.MISSING_CREATED_DATE);
    }
}
