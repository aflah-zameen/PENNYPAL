package com.application.pennypal.application.service.subscription;

import com.application.pennypal.application.dto.output.subscription.UserSubscriptionOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.subscription.PurchasePlan;
import com.application.pennypal.application.port.out.repository.SubscriptionPlanRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserSubscriptionRepositoryPort;
import com.application.pennypal.domain.subscription.SubscriptionPlan;
import com.application.pennypal.domain.subscription.UserSubscription;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class PurchasePlanService implements PurchasePlan {
    private final SubscriptionPlanRepositoryPort subscriptionPlanRepositoryPort;
    private final UserSubscriptionRepositoryPort userSubscriptionRepositoryPort;
    @Override
    public UserSubscriptionOutputModel execute(String userId, String planId) {
        SubscriptionPlan plan = subscriptionPlanRepositoryPort.getPlan(planId)
                .orElseThrow(() -> new ApplicationBusinessException("Subscription plan not found","NOT_FOUND"));
        if(userSubscriptionRepositoryPort.existsUser(userId)){
            throw new ApplicationBusinessException("User has already an active subscription","CONFLICT");
        }
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(plan.getDurationDays());

        UserSubscription sub = UserSubscription.create(
                userId,
                planId,
                start,
                end
        );
        sub = userSubscriptionRepositoryPort.save(sub,plan.getPlanId());
        return new UserSubscriptionOutputModel(
                sub.getSubscriptionId(),
                sub.getUserId(),
                sub.getPlanId(),
                sub.getStartDate(),
                sub.getEndDate()
        );
    }
}
