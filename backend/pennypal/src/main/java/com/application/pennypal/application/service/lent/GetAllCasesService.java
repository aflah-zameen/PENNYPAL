package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.dto.output.lend.LoanCaseOutputModel;
import com.application.pennypal.application.mappers.lent.LoanCaseApplicationMapper;
import com.application.pennypal.application.port.in.transaction.GetAllLoanCases;
import com.application.pennypal.application.port.out.repository.LoanCaseRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllCasesService implements GetAllLoanCases {
    private final LoanCaseRepositoryPort loanCaseRepositoryPort;
    private final LoanCaseApplicationMapper loanCaseApplicationMapper;
    @Override
    public List<LoanCaseOutputModel> execute() {
        return loanCaseRepositoryPort.getAll().stream()
                .map(loanCaseApplicationMapper::toOutput)
                .toList();
    }
}
