package com.application.pennypal.application.dto.output.sale;

import java.math.BigDecimal;

public record SubscriptionRevenueOutput(
        String type,
        BigDecimal revenue
) {
}
