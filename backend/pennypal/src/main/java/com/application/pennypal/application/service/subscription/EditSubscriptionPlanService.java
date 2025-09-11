package com.application.pennypal.application.service.subscription;

import com.application.pennypal.application.dto.input.subscription.SubscriptionPlanInputModel;
import com.application.pennypal.application.dto.output.subscription.PlanOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.subscription.EditSubscriptionPlan;
import com.application.pennypal.application.port.out.repository.SubscriptionPlanRepositoryPort;
import com.application.pennypal.domain.subscription.SubscriptionPlan;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditSubscriptionPlanService implements EditSubscriptionPlan {
    private final SubscriptionPlanRepositoryPort planRepositoryPort;
    @Override
    public PlanOutputModel edit(String planId,SubscriptionPlanInputModel inputModel) {
        SubscriptionPlan plan = planRepositoryPort.getPlan(planId)
                        .orElseThrow(() -> new ApplicationBusinessException("Plan not found","NOT_FOUND"));
        plan.setName(inputModel.name());
        plan.setAmount(inputModel.amount());
        plan.setDurationDays(inputModel.durationDays());
        plan = planRepositoryPort.update(plan);
        return new PlanOutputModel(
                plan.getPlanId(),
                plan.getName(),
                plan.getDescription(),
                plan.getAmount(),
                plan.getDurationDays(),
                plan.getFeatures(),
                plan.getCreatedAt()
        );
    }
}
