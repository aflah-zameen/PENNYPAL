package com.application.pennypal.application.dto.input;

import java.time.LocalDate;
import java.util.List;

public record AnalyticsFilters(
        LocalDate start,
        LocalDate end,
        List<String> subscriptionType,
        List<String> paymentStatus,
        String period
) {
}
