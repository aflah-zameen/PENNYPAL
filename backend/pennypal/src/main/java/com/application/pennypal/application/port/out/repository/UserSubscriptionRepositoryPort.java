package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.sale.SubscriptionBreakdownOutput;
import com.application.pennypal.domain.subscription.SubscriptionPlan;
import com.application.pennypal.domain.subscription.UserSubscription;

import java.time.LocalDate;
import java.util.List;

public interface UserSubscriptionRepositoryPort {
    boolean existsUser(String userId);
    UserSubscription save(UserSubscription sub, String planId);

    boolean hasActiveSubscription(String userId, LocalDate today);

    List<UserSubscription> findByPlanIdAndDateRange(String planId, LocalDate start, LocalDate end);

    int countByIsActiveTrue();

    List<SubscriptionBreakdownOutput> findBreakdownByPlan(LocalDate start, LocalDate end);

    List<UserSubscription> findByDateRangeAndPlans(LocalDate start, LocalDate end, List<String> planIds);
}
