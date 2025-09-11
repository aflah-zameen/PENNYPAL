package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.application.mappers.lent.LentApplicationMapper;
import com.application.pennypal.application.port.in.lent.GetLoansToRepay;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetLoansToRepayService implements GetLoansToRepay {
    private final LoanRepositoryPort loanRepositoryPort;
    private final LentApplicationMapper lentApplicationMapper;
    @Override
    public List<LoanOutputModel> execute(String userId) {
        return loanRepositoryPort.getLoansToRepay(userId).stream()
                .map(lentApplicationMapper::toOutput)
                .toList();
    }
}
