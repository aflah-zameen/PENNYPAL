package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.port.in.lent.UpdateLoanCaseAction;
import com.application.pennypal.application.port.out.repository.LoanCaseRepositoryPort;
import com.application.pennypal.domain.LoanCase.LoanCase;
import com.application.pennypal.domain.LoanCase.LoanCaseStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UpdateLoanCaseActionService implements UpdateLoanCaseAction {
    private final LoanCaseRepositoryPort loanCaseRepositoryPort;
    @Override
    public void execute(String caseId,LoanCaseStatus status) {
        LoanCase loanCase = loanCaseRepositoryPort.getLoanCase(caseId);
        loanCase.setStatus(status);
        loanCase.setClosedAt(LocalDateTime.now());
        loanCaseRepositoryPort.update(loanCase);
    }
}
