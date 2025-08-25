package com.application.pennypal.application.service.lend;

import com.application.pennypal.application.dto.output.lend.LendingSummaryOutputModel;
import com.application.pennypal.application.port.in.lend.GetLendingSummary;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class GetLendingSummaryService implements GetLendingSummary {
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    private final LoanRepositoryPort loanRepositoryPort;
    @Override
    public LendingSummaryOutputModel execute(String userId) {
        BigDecimal totalAmountLent = loanRepositoryPort.getTotalAmountLent(userId);
        BigDecimal totalAmountBorrowed = loanRepositoryPort.getTotalAmountBorrowed(userId);
        long activeLoanSent = loanRepositoryPort.getActiveLoanSentCount(userId);
        long activeLoanReceived = loanRepositoryPort.getActiveLoanReceived(userId);
        long overdueLoanCount = loanRepositoryPort.getOverdueLoanCount(userId);
        long requestCount = lendingRequestRepositoryPort.getLendingRequestSentCount(userId);

        return new LendingSummaryOutputModel(
                totalAmountLent,
                totalAmountBorrowed,
                activeLoanSent,
                activeLoanReceived,
                overdueLoanCount,
                requestCount
        );
    }
}
