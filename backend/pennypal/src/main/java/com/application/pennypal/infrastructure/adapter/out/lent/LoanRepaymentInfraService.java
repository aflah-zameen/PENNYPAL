package com.application.pennypal.infrastructure.adapter.out.lent;

import com.application.pennypal.application.dto.input.transaction.TransferInputModel;
import com.application.pennypal.application.dto.output.lend.LoanRepaymentOutputModel;
import com.application.pennypal.application.dto.output.lend.RepayTransactionOutputModel;
import com.application.pennypal.application.dto.output.user.TransferTransaction;
import com.application.pennypal.application.port.in.lent.LoanRepayment;
import com.application.pennypal.application.port.in.transaction.TransferMoney;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanRepaymentInfraService {
    private final LoanRepayment loanRepayment;
    private final TransferMoney transferMoney;

    @Transactional
    public RepayTransactionOutputModel repay(String userId, String loanId, TransferInputModel inputModel){
        LoanRepaymentOutputModel outputModel = loanRepayment.execute(userId,loanId,inputModel);
        TransferTransaction transaction = transferMoney.execute(inputModel);
        return new RepayTransactionOutputModel(
                transaction.id(),
                transaction.senderId(),
                transaction.recipientId(),
                transaction.amount(),
                transaction.note(),
                transaction.date(),
                transaction.status(),
                transaction.failureReason(),
                transaction.type(),
                outputModel.coins()
        );
    }
}
