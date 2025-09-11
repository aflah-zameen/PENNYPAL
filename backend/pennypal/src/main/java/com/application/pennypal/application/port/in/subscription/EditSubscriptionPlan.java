package com.application.pennypal.application.port.in.subscription;

import com.application.pennypal.application.dto.input.subscription.SubscriptionPlanInputModel;
import com.application.pennypal.application.dto.output.subscription.PlanOutputModel;

public interface EditSubscriptionPlan {
    PlanOutputModel edit (String planId,SubscriptionPlanInputModel inputModel);
}
