package com.application.pennypal.application.port.in.lent;

import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.domain.valueObject.UserDomainDTO;

public interface RemindLoanPayment {
    LoanOutputModel execute(UserDomainDTO user, String loanId);
}
