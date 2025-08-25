package com.application.pennypal.application.dto.output.subscription;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserSubscriptionOutputModel(
        String subscriptionId,
        String userId,
        String planId,
        LocalDate start,
        LocalDate end
) {
}
