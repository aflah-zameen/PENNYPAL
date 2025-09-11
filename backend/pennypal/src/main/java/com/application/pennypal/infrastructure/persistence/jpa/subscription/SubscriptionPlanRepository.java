package com.application.pennypal.infrastructure.persistence.jpa.subscription;

import com.application.pennypal.domain.subscription.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlanEntity,Long> {
    Optional<SubscriptionPlanEntity> findByPlanId(String planId);

    List<SubscriptionPlanEntity> findAllByDeletedFalse();
}
