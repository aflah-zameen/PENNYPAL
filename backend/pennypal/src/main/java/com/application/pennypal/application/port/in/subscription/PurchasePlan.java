package com.application.pennypal.application.port.in.subscription;

import com.application.pennypal.application.dto.output.subscription.UserSubscriptionOutputModel;

public interface PurchasePlan {
    UserSubscriptionOutputModel execute(String userId, String planId);
}
