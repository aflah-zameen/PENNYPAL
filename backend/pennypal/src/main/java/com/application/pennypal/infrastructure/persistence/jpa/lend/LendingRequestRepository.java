package com.application.pennypal.infrastructure.persistence.jpa.lend;

import com.application.pennypal.domain.lend.LendingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LendingRequestRepository extends JpaRepository<LendingRequestEntity,Long> {
    List<LendingRequestEntity> findAllByRequestedBy(String userId);

    List<LendingRequestEntity> findAllByRequestedTo(String userId);

    Optional<LendingRequestEntity> findByRequestId(String requestId);
}
