package com.application.pennypal.application.dto.output.transaction;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import java.math.BigDecimal;

public record SpendSummaryOutputModel(
        BigDecimal totalSpend,
        CategorySpendingOutputModel topCategory,
        MostUsedCard mostUsedCard,
        MonthlyComparison monthlyComparison
) {
}
