package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.dto.input.transaction.TransferInputModel;
import com.application.pennypal.application.dto.output.lend.LoanRepaymentOutputModel;
import com.application.pennypal.application.dto.output.user.TransferTransaction;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.lent.LoanRepayment;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import com.application.pennypal.application.service.coin.CoinRewardService;
import com.application.pennypal.domain.lend.Loan;
import com.application.pennypal.domain.lend.LoanStatus;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class LoanRepaymentService implements LoanRepayment {
    private final LoanRepositoryPort loanRepositoryPort;
    private final CoinRewardService coinRewardService;
    @Override
    public LoanRepaymentOutputModel execute(String userId, String loanId, TransferInputModel inputModel) {
        Loan loan = loanRepositoryPort.getLoan(loanId)
                .orElseThrow(() -> new ApplicationBusinessException("Loan not found","NOT_FOUND"));
        loan = loan.recordPayment(inputModel.amount());
        loan = loanRepositoryPort.update(loan);
        if(loan.getStatus().equals(LoanStatus.PAID) && loan.getDeadline().isAfter(LocalDateTime.now())){
            BigDecimal coins = coinRewardService.addCoinsForLoanRepayment(userId,loanId);
            return new LoanRepaymentOutputModel(coins);
        }
        return new LoanRepaymentOutputModel(null);
    }
}
