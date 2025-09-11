package com.application.pennypal.infrastructure.adapter.out.lent;

import com.application.pennypal.application.dto.input.transaction.TransferInputModel;
import com.application.pennypal.application.dto.output.user.TransferTransaction;
import com.application.pennypal.application.port.in.lent.ApproveLendingRequest;
import com.application.pennypal.infrastructure.adapter.out.Transaction.TransferInfrastructureService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanGrantInfraService {
    private final TransferInfrastructureService transferInfrastructureService;
    private final ApproveLendingRequest approveLendingRequest;

    @Transactional
    public TransferTransaction grantLoan(String userId,String requestId,TransferInputModel inputModel){
        approveLendingRequest.execute(userId,requestId);
        return transferInfrastructureService.transfer(inputModel);
    }
}
