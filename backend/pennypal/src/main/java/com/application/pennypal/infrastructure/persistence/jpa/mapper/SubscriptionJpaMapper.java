package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.subscription.SubscriptionPlan;
import com.application.pennypal.domain.subscription.UserSubscription;
import com.application.pennypal.infrastructure.persistence.jpa.subscription.SubscriptionPlanEntity;
import com.application.pennypal.infrastructure.persistence.jpa.subscription.UserSubscriptionEntity;

public class SubscriptionJpaMapper {
    public static SubscriptionPlan toDomain(SubscriptionPlanEntity entity){
        return SubscriptionPlan.reconstruct(
                entity.getPlanId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getDurationDays(),
                entity.getFeatures(),
                entity.isDeleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static SubscriptionPlanEntity toEntity (SubscriptionPlan plan){
        return new SubscriptionPlanEntity(
                plan.getPlanId(),
                plan.getName(),
                plan.getDescription(),
                plan.getAmount(),
                plan.getDurationDays(),
                plan.getFeatures()
        );
    }

    public static UserSubscription toDomain(UserSubscriptionEntity entity){
        return new UserSubscription(
                entity.getSubscriptionId(),
                entity.getUserId(),
                entity.getPlan().getPlanId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserSubscriptionEntity toEntity(UserSubscription userSubscription,SubscriptionPlanEntity plan){
        return new UserSubscriptionEntity(
                userSubscription.getSubscriptionId(),
                userSubscription.getUserId(),
                plan,
                userSubscription.getStartDate(),
                userSubscription.getEndDate()
        );
    }
}
