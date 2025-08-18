package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.domain.lend.LendingRequestStatus;

import java.util.List;
import java.util.Optional;

public interface LendingRequestRepositoryPort {
    LendingRequest save(LendingRequest lendingRequest);
    LendingRequest update(LendingRequest lendingRequest);
    List<LendingRequest> findAllUserRequests(String userId);
    List<LendingRequest> findAllLendingRequests(String userId);
    Optional<LendingRequest> findByRequestId(String s);
}
