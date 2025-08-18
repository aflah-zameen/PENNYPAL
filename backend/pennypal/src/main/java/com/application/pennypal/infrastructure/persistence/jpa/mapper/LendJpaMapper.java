package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LendingRequestEntity;

public class LendJpaMapper {
    public static LendingRequest toDomain(LendingRequestEntity entity){
        return LendingRequest.reconstruct(
                entity.getRequestId(),
                entity.getRequestedBy(),
                entity.getRequestedTo(),
                entity.getAmount(),
                entity.getMessage(),
                entity.getProposedDeadline(),
                entity.getAcceptedDeadline(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static LendingRequestEntity toEntity(LendingRequest lendingRequest){
        return new LendingRequestEntity(
                lendingRequest.getRequestId(),
                lendingRequest.getRequestedBy(),
                lendingRequest.getRequestedTo(),
                lendingRequest.getAmount(),
                lendingRequest.getMessage(),
                lendingRequest.getProposedDeadline(),
                lendingRequest.getAcceptedDeadline(),
                lendingRequest.getStatus()
        );
    }
}
