package com.application.pennypal.infrastructure.adapter.out.lent;

import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.application.port.in.lent.RemindLoanPayment;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemindLoanPaymentInfraService {
    private final RemindLoanPayment remindLoanPayment;

    @Transactional
    public LoanOutputModel remind(UserDomainDTO user,String loanId){
        return remindLoanPayment.execute(user,loanId);
    }
}
