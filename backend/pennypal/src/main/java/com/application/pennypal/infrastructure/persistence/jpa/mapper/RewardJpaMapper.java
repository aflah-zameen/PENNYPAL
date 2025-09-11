package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.reward.RewardPolicy;
import com.application.pennypal.infrastructure.persistence.jpa.reward.RewardPolicyEntity;

public class RewardJpaMapper {
    public static RewardPolicy toDomain(RewardPolicyEntity entity){
        return RewardPolicy.builder()
                .id(entity.getId())
                .coinAmount(entity.getCoinAmount())
                .actionType(entity.getActionType())
                .deleted(entity.isDeleted())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static  RewardPolicyEntity toEntity(RewardPolicy domain){
        return RewardPolicyEntity.builder()
                .id(domain.getId())
                .actionType(domain.getActionType())
                .coinAmount(domain.getCoinAmount())
                .deleted(domain.isDeleted())
                .active(domain.isActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
