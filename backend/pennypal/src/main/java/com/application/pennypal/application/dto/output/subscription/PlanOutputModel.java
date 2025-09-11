package com.application.pennypal.application.dto.output.subscription;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PlanOutputModel(
        String id,
        String name,
        String description,
        BigDecimal amount,
        int durationDays,
        List<String> features,
        LocalDateTime createdDate
) {
}
