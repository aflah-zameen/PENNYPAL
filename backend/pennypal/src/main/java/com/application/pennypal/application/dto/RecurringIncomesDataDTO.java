package com.application.pennypal.application.dto;

import java.math.BigDecimal;
import java.util.List;

public record RecurringIncomesDataDTO(List<RecurringIncomeDTO> recurringIncomeDTOS , BigDecimal monthlyTotal, int totalRecurring) {
}
