package com.application.pennypal.domain.exception.shared;

import com.application.pennypal.domain.exception.base.DomainBusinessException;

public class MissingAmountExceptionDomain extends DomainBusinessException {
    public MissingAmountExceptionDomain()
    {
        super("Amount must not be null");
    }
}
