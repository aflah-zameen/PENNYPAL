package com.application.pennypal.application.port.in.lent;

import com.application.pennypal.domain.LoanCase.LoanCaseStatus;

public interface UpdateLoanCaseAction {
    void execute(String caseId,LoanCaseStatus status);
}
