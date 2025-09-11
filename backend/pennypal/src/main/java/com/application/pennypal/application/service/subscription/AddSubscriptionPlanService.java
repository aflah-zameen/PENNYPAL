package com.application.pennypal.application.service.subscription;

import com.application.pennypal.application.dto.input.subscription.SubscriptionPlanInputModel;
import com.application.pennypal.application.dto.output.subscription.PlanOutputModel;
import com.application.pennypal.application.port.in.subscription.AddSubscriptionPlan;
import com.application.pennypal.application.port.out.repository.SubscriptionPlanRepositoryPort;
import com.application.pennypal.domain.subscription.SubscriptionPlan;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddSubscriptionPlanService implements AddSubscriptionPlan {
    private final SubscriptionPlanRepositoryPort planRepositoryPort;
    @Override
    public PlanOutputModel execute(SubscriptionPlanInputModel inputModel) {
        SubscriptionPlan subscriptionPlan = SubscriptionPlan.create(
                inputModel.name(),
                inputModel.description(),
                inputModel.amount(),
                inputModel.durationDays(),
                inputModel.features()
        );
        subscriptionPlan = planRepositoryPort.save(subscriptionPlan);
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
