package com.application.pennypal.application.dto.output.transaction;

import java.math.BigDecimal;

public record MonthlyComparison(
      Double percentage,
      String trend,
      BigDecimal previous
) {
}
