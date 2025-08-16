package com.application.pennypal.application.dto.output.income;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PendingIncomeOutput(Long incomeId, String title,
                                  BigDecimal amount, LocalDate incomeDate,
                                  CategoryUserOutput category) {
}
