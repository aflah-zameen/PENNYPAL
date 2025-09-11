package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.domain.lend.LendingRequest;

import java.util.List;
import java.util.Optional;

public interface LendingRequestRepositoryPort {
    LendingRequest save(LendingRequest lendingRequest);
    LendingRequest update(LendingRequest lendingRequest);
    Optional<LendingRequest> findByRequestId(String s);
    List<LendingRequest> getAllLendingRequestsSent(String userId);
    List<LendingRequest> getAllLendingRequestsReceived(String userId);
    long getLendingRequestSentCount(String userId);
}
