package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.domain.subscription.SubscriptionPlan;

import java.util.List;
import java.util.Optional;

public interface SubscriptionPlanRepositoryPort {
    Optional<SubscriptionPlan> getPlan(String planId);

    List<SubscriptionPlan> getAllPlan();

    SubscriptionPlan save(SubscriptionPlan subscriptionPlan);

    SubscriptionPlan update(SubscriptionPlan plan);
}
