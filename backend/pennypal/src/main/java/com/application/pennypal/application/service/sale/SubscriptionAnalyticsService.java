package com.application.pennypal.application.service.sale;

import com.application.pennypal.application.dto.input.AnalyticsFilters;
import com.application.pennypal.application.dto.output.sale.*;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.sale.SubscriptionAnalytics;
import com.application.pennypal.application.port.out.repository.SubscriptionPlanRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserSubscriptionRepositoryPort;
import com.application.pennypal.domain.subscription.SubscriptionPlan;
import com.application.pennypal.domain.subscription.UserSubscription;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SubscriptionAnalyticsService implements SubscriptionAnalytics {
    private final SubscriptionPlanRepositoryPort subscriptionPlanRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final UserSubscriptionRepositoryPort userSubscriptionRepositoryPort;

    @Override
    public SubscriptionAnalyticsOutput getAnalyticsSummary(AnalyticsFilters filters) {
        BigDecimal totalRevenue = transactionRepositoryPort.sumAmountByStatusAndDateRange(filters.paymentStatus(), filters.start(), filters.end());
        int activeSubscriptions = userSubscriptionRepositoryPort.countByIsActiveTrue();
        List<SubscriptionBreakdownOutput> planSummaries = userSubscriptionRepositoryPort.findBreakdownByPlan(filters.start(), filters.end());

        return new SubscriptionAnalyticsOutput(totalRevenue, activeSubscriptions, planSummaries);
    }

    @Override
    public List<SalesDataOutput> getSalesData(AnalyticsFilters filters) {
        LocalDate start = filters.start();
        LocalDate end = filters.end();
        List<String> planIds = filters.subscriptionType();

        List<SalesDataOutput> raw = transactionRepositoryPort.findSalesGroupedByMonthAndPlan(start, end);

        // Optional: filter by planIds if provided
        if (planIds != null && !planIds.isEmpty()) {
            return raw.stream()
                    .filter(row -> planIds.contains(row.getPlanId()))
                    .peek(data -> {
                        String planId = data.getPlanId();
                        SubscriptionPlan plan = subscriptionPlanRepositoryPort.getPlan(planId)
                                .orElseThrow(() -> new ApplicationBusinessException("Plan not found","NOT_FOUND"));
                        data.setPlanName(plan.getName());
                    })
                    .toList();
        }

        return raw;
    }


    @Override
    public List<SubscriptionBreakdownOutput> getSubscriptionBreakdown(AnalyticsFilters filters) {
        LocalDate start = filters.start();
        LocalDate end = filters.end();
        List<String> planIds = filters.subscriptionType();

        List<UserSubscription> subscriptions = userSubscriptionRepositoryPort.findByDateRangeAndPlans(start, end, planIds);

        Map<String, List<UserSubscription>> grouped = subscriptions.stream()
                .collect(Collectors.groupingBy(UserSubscription::getPlanId));

        List<SubscriptionBreakdownOutput> result = new ArrayList<>();

        for (Map.Entry<String, List<UserSubscription>> entry : grouped.entrySet()) {
            String planId = entry.getKey();
            List<UserSubscription> subs = entry.getValue();

            long count = subs.size();
            BigDecimal revenue = subs.stream()
                    .map(sub -> {
                        String planId1 = sub.getPlanId();
                        SubscriptionPlan plan = subscriptionPlanRepositoryPort.getPlan(planId1)
                                .orElseThrow(() -> new ApplicationBusinessException("Plan not found","NOT_FOUND"));
                        return plan.getAmount();
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            result.add(new SubscriptionBreakdownOutput(planId, count, revenue));
        }

        return result;
    }

    @Override
    public List<PaymentStatusDTO> getPaymentStatusData(AnalyticsFilters filters) {
        LocalDate start = filters.start();
        LocalDate end = filters.end();

        List<PaymentStatusDTO> rows = transactionRepositoryPort.findPaymentStatusSummary(start, end);

        List<String> order = List.of("COMPLETED", "PENDING", "FAILED", "CANCELLED");
        return rows.stream()
                .sorted(Comparator.comparingInt(dto -> {
                    int idx = order.indexOf(dto.status().getValue());
                    return idx >= 0 ? idx : Integer.MAX_VALUE;
                }))
                .toList();
    }

}
