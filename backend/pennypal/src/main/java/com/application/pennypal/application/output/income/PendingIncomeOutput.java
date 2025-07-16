package com.application.pennypal.application.output.income;

import com.application.pennypal.application.output.category.CategoryUserOutput;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PendingIncomeOutput(Long incomeId, String title,
                                  BigDecimal amount, LocalDate incomeDate,
                                  CategoryUserOutput category) {
}
