package com.application.pennypal.domain.exception.shared;

import com.application.pennypal.domain.exception.base.DomainBusinessException;

public class MissingCreatedAtExceptionDomain extends DomainBusinessException {
    public MissingCreatedAtExceptionDomain(){
        super("Created timestamp must not be null");
    }
}
