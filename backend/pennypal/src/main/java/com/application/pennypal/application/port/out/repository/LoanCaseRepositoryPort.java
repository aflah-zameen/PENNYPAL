package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.lend.LoanCaseOutputModel;
import com.application.pennypal.domain.LoanCase.LoanCase;

import java.util.List;

public interface LoanCaseRepositoryPort {
    LoanCase save(LoanCase loanCase);
    List<LoanCase> getAll();

    LoanCase getLoanCase(String caseId);

    void update(LoanCase loanCase);
}
