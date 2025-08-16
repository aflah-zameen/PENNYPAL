package com.application.pennypal.domain.exception.transaction;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;

public class MissingTransactionDateExceptionDomain extends DomainValidationException {
    public MissingTransactionDateExceptionDomain(){
        super("Transaction date must not be null", DomainErrorCode.MISSING_TRANSACTION_DATE);
    }
}
