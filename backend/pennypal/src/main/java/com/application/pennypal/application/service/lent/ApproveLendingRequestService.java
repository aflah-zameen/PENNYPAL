package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.lent.ApproveLendingRequest;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import com.application.pennypal.domain.lend.LendingRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApproveLendingRequestService implements ApproveLendingRequest {
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    private final LoanRepositoryPort loanRepositoryPort;
    @Override
    public void execute(String userId, String requestId) {
        LendingRequest lendingRequest = lendingRequestRepositoryPort.findByRequestId(requestId)
                .orElseThrow(() -> new ApplicationBusinessException("Lending request can't be found","NOT_FOUND"));
        if(!lendingRequest.getRequestedTo().equals(userId)){
            throw new ApplicationBusinessException("User cannot perform this action","INVALID_ACTION");
        }
        lendingRequest = lendingRequest.acceptRequest(lendingRequest.getProposedDeadline());
        lendingRequest = lendingRequest.setAcceptedDeadline(lendingRequest.getProposedDeadline());
        loanRepositoryPort.save(lendingRequest.getLoan());
        lendingRequestRepositoryPort.update(lendingRequest);
    }
}
