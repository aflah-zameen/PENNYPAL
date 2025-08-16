package com.application.pennypal.domain.exception.category;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class CategoryRequiredExceptionDomain extends DomainBusinessException {
    public CategoryRequiredExceptionDomain(){
        super("Category is required for income or expense transaction",DomainErrorCode.REQUIRED_CATEG0RY_ID);
    }
}
