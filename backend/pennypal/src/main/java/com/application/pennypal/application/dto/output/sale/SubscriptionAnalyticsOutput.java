package com.application.pennypal.application.dto.output.sale;

import java.math.BigDecimal;
import java.util.List;

public record SubscriptionAnalyticsOutput(
        BigDecimal totalRevenue,
        int activeSubscriptions,
        List<SubscriptionBreakdownOutput> planSummaries
) {
}
