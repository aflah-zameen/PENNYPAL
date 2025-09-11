package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.lent.CancelLendingRequest;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.domain.lend.LendingRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CancelLendingRequestService implements CancelLendingRequest {
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    @Override
    public void execute(String userId, String requestId) {
        LendingRequest lendingRequest = lendingRequestRepositoryPort.findByRequestId(requestId)
                .orElseThrow(() -> new ApplicationBusinessException("Lending Request not found","NOT_FOUND"));

        lendingRequest = lendingRequest.cancelRequest();
        lendingRequestRepositoryPort.save(lendingRequest);
    }
}
