package com.application.pennypal.application.service.lend;

import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.application.mappers.lent.LentApplicationMapper;
import com.application.pennypal.application.port.in.lend.GetLoansToCollect;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetLoansToCollectService implements GetLoansToCollect {
    private final LoanRepositoryPort loanRepositoryPort;
    private final LentApplicationMapper lentApplicationMapper;
    @Override
    public List<LoanOutputModel> execute(String userId) {
        return loanRepositoryPort.getLoansToCollect(userId).stream()
                .map(lentApplicationMapper::toOutput)
                .toList();
    }
}
