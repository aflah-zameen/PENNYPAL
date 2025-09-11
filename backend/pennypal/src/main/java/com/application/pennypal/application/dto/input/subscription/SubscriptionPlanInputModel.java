package com.application.pennypal.application.dto.input.subscription;

import java.math.BigDecimal;
import java.util.List;

public record SubscriptionPlanInputModel(
    String name,
    String description,
    BigDecimal amount,
    int durationDays,
    List<String> features
) {
}
