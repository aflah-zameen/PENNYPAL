package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.subscription.SubscriptionPlan;
import com.application.pennypal.infrastructure.persistence.jpa.subscription.SubscriptionPlanEntity;

public class SubscriptionJpaMapper {
    public static SubscriptionPlan toDomain(SubscriptionPlanEntity entity){
        return SubscriptionPlan.reconstruct(
                entity.getPlanId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDurationDays(),
                entity.getFeatures(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static SubscriptionPlan toEntity
}
