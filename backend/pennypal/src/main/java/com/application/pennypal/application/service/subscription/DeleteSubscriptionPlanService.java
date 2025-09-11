package com.application.pennypal.application.service.subscription;

import com.application.pennypal.application.dto.output.subscription.PlanOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.subscription.DeleteSubscriptionPlan;
import com.application.pennypal.application.port.out.repository.SubscriptionPlanRepositoryPort;
import com.application.pennypal.domain.subscription.SubscriptionPlan;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteSubscriptionPlanService implements DeleteSubscriptionPlan {
    private final SubscriptionPlanRepositoryPort subscriptionPlanRepositoryPort;
    @Override
    public PlanOutputModel execute(String planId) {
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepositoryPort.getPlan(planId)
                .orElseThrow(() -> new ApplicationBusinessException("Subscription not found","NOT_FOUND"));
        subscriptionPlan.setDeleted(true);
        subscriptionPlan = subscriptionPlanRepositoryPort.update(subscriptionPlan);
        return new PlanOutputModel(
                subscriptionPlan.getPlanId(),
                subscriptionPlan.getName(),
                subscriptionPlan.getDescription(),
                subscriptionPlan.getAmount(),
                subscriptionPlan.getDurationDays(),
                subscriptionPlan.getFeatures(),
                subscriptionPlan.getCreatedAt()
        );
    }
}
