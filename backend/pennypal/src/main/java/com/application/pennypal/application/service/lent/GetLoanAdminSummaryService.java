package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.dto.output.lend.LoanAdminSummary;
import com.application.pennypal.application.port.in.lent.GetLoanAdminSummary;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetLoanAdminSummaryService implements GetLoanAdminSummary {
    private final LoanRepositoryPort loanRepositoryPort;
    @Override
    public LoanAdminSummary execute() {
        return loanRepositoryPort.getLoanAdminSummary();
    }
}
