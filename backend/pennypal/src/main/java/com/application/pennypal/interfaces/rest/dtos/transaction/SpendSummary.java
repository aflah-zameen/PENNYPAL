package com.application.pennypal.interfaces.rest.dtos.transaction;

import com.application.pennypal.application.dto.output.transaction.MonthlyComparison;
import com.application.pennypal.application.dto.output.transaction.MostUsedCard;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;

import java.math.BigDecimal;


public record SpendSummary(
        BigDecimal totalSpend,
        CategoryUserResponseDTO topCategory,
        MostUsedCard mostUsedCard,
        MonthlyComparison monthlyComparison
) {
}
