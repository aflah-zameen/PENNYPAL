package com.application.pennypal.domain.exception.transaction;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;

public class MissingTransactionIdExceptionDomain extends DomainValidationException {
    public MissingTransactionIdExceptionDomain(){
        super("Transaction ID must not be null", DomainErrorCode.MISSING_TRANSACTION_ID);
    }
}
