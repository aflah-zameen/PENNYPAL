package com.application.pennypal.domain.exception.transaction;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class TransferUsersRequiredExceptionDomain extends DomainBusinessException {
    public TransferUsersRequiredExceptionDomain()
    {
        super("Transfer transactions must include both source and target users", DomainErrorCode.TRANSFER_USER_REQUIRED);
    }
}
