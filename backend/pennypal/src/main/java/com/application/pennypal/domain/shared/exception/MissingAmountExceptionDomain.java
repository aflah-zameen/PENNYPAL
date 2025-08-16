package com.application.pennypal.domain.shared.exception;

import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class MissingAmountExceptionDomain extends DomainBusinessException {
    public MissingAmountExceptionDomain()
    {
        super("Amount must not be null",DomainErrorCode.INVALID_AMOUNT);
    }
}
