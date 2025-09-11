package com.application.pennypal.application.port.in.lent;

import com.application.pennypal.application.dto.input.transaction.TransferInputModel;
import com.application.pennypal.application.dto.output.lend.LoanRepaymentOutputModel;
import com.application.pennypal.application.dto.output.user.TransferTransaction;

public interface LoanRepayment {
    LoanRepaymentOutputModel execute(String userId,String loanId, TransferInputModel inputModel);
}
