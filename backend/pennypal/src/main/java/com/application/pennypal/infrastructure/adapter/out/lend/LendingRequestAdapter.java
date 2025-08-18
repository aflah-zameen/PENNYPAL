package com.application.pennypal.infrastructure.adapter.out.lend;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LendingRequestEntity;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LendingRequestRepository;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.LendJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LendingRequestAdapter implements LendingRequestRepositoryPort {
    private final LendingRequestRepository lendingRequestRepository;
    @Override
    public LendingRequest save(LendingRequest lendingRequest) {
        LendingRequestEntity entity = LendJpaMapper.toEntity(lendingRequest);
        entity = lendingRequestRepository.save(entity);
        return LendJpaMapper.toDomain(entity);
    }

    @Override
    public LendingRequest update(LendingRequest lendingRequest) {
        LendingRequestEntity requestEntity = lendingRequestRepository.findByRequestId(lendingRequest.getRequestId())
                .orElseThrow(() -> new ApplicationBusinessException("Lending Request jpa entity","NOT_FOUND"));
        requestEntity.setStatus(lendingRequest.getStatus());
        requestEntity = lendingRequestRepository.save(requestEntity);
        return LendJpaMapper.toDomain(requestEntity);
    }

    @Override
    public List<LendingRequest> findAllUserRequests(String userId) {
        return lendingRequestRepository.findAllByRequestedBy(userId).stream()
                .map(LendJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<LendingRequest> findAllLendingRequests(String userId) {
        return lendingRequestRepository.findAllByRequestedTo(userId).stream()
                .map(LendJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<LendingRequest> findByRequestId(String requestId) {
        return lendingRequestRepository.findByRequestId(requestId)
                .map(LendJpaMapper::toDomain);
    }
}
