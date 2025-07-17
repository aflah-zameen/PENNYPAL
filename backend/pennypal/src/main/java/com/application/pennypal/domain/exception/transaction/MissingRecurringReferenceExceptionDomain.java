package com.application.pennypal.domain.exception.transaction;

import com.application.pennypal.domain.exception.base.DomainBusinessException;

public class MissingRecurringReferenceExceptionDomain extends DomainBusinessException {
    public MissingRecurringReferenceExceptionDomain() {
        super("Recurring transactions must include a valid recurring transaction ID");
    }
}
