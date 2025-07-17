package com.application.pennypal.application.output.expense;

import com.application.pennypal.application.output.category.CategoryUserOutput;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PendingExpenseOutput(Long expenseId, String title,
                                   BigDecimal amount, LocalDate paymentDate,
                                   CategoryUserOutput category) {
}
