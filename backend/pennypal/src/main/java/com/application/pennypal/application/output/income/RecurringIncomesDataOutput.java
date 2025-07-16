package com.application.pennypal.application.output.income;

import java.math.BigDecimal;
import java.util.List;

public record RecurringIncomesDataOutput(List<RecurringIncomeOutput> recurringIncomeDTOS , BigDecimal monthlyTotal, int totalRecurring) {
}
