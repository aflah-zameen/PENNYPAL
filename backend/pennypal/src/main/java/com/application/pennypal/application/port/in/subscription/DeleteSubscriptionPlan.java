package com.application.pennypal.application.port.in.subscription;

import com.application.pennypal.application.dto.output.subscription.PlanOutputModel;

public interface DeleteSubscriptionPlan {
    PlanOutputModel execute(String planId);
}
