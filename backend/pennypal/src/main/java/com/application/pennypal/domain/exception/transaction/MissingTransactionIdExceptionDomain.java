package com.application.pennypal.domain.exception.transaction;

import com.application.pennypal.domain.exception.base.DomainValidationException;

public class MissingTransactionIdExceptionDomain extends DomainValidationException {
    public MissingTransactionIdExceptionDomain(){
        super("Transaction ID must not be null");
    }
}
