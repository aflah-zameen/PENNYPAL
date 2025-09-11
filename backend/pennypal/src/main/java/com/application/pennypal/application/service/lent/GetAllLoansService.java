package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.application.mappers.lent.LentApplicationMapper;
import com.application.pennypal.application.port.in.lent.GetAllLoans;
import com.application.pennypal.application.port.in.lent.LoanFilterInputModel;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllLoansService implements GetAllLoans {
    private final LoanRepositoryPort loanRepositoryPort;
    private final LentApplicationMapper lentApplicationMapper;
    @Override
    public List<LoanOutputModel> execute() {
        return loanRepositoryPort.getAll().stream()
                .map(lentApplicationMapper::toOutput)
                .toList();
    }

    @Override
    public List<LoanOutputModel> getFiltered(LoanFilterInputModel inputModel) {
        return loanRepositoryPort.getFilteredAll(inputModel).stream()
                .map(lentApplicationMapper::toOutput)
                .toList();
    }
}
