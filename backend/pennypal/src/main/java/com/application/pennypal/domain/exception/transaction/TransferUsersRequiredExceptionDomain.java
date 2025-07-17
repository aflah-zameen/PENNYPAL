package com.application.pennypal.domain.exception.transaction;

import com.application.pennypal.domain.exception.base.DomainBusinessException;

public class TransferUsersRequiredExceptionDomain extends DomainBusinessException {
    public TransferUsersRequiredExceptionDomain()
    {
        super("Transfer transactions must include both source and target users");
    }
}
