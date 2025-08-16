package com.application.pennypal.domain.shared.exception;

import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class NegativeAmountNotAllowedExceptionDomain extends DomainBusinessException {
    public NegativeAmountNotAllowedExceptionDomain(){
        super("Amount must be positive",DomainErrorCode.INVALID_AMOUNT);
    }
}
