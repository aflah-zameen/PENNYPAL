package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.dto.input.lend.LoanCaseInputModel;
import com.application.pennypal.application.dto.output.lend.LoanCaseOutputModel;
import com.application.pennypal.application.port.in.lent.FileLoanCase;
import com.application.pennypal.application.port.out.repository.LoanCaseRepositoryPort;
import com.application.pennypal.domain.LoanCase.LoanCase;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class FileLoanCAseService implements FileLoanCase {
    private final LoanCaseRepositoryPort caseRepositoryPort;
    @Override
    public LoanCaseOutputModel execute(String userId,,LoanCaseInputModel inputModel) {
        LoanCase loanCase = LoanCase.create(
                inputModel.loanId(),
                userId,
                inputModel.reason(),
                LocalDateTime.now()
        );
        caseRepositoryPort.save(loanCase);
    }
}
