package com.application.pennypal.application.dto.output.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DashIncExpChart(
        String date,
        BigDecimal income,
        BigDecimal expense
) {
}
