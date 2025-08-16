package com.application.pennypal.domain.user.exception.business;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class UserRoleNotFoundDomainException extends DomainBusinessException {
    public UserRoleNotFoundDomainException(String message) {
        super(message, DomainErrorCode.USER_ROLE_NOT_FOUND);
    }
}
