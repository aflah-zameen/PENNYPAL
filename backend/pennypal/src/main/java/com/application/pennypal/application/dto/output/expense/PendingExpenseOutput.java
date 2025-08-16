package com.application.pennypal.application.dto.output.expense;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PendingExpenseOutput(Long expenseId, String title,
                                   BigDecimal amount, LocalDate paymentDate,
                                   CategoryUserOutput category) {
}
