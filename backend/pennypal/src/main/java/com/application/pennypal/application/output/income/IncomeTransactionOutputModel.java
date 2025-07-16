package com.application.pennypal.application.output.income;

import com.application.pennypal.application.output.category.CategoryUserOutput;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeTransactionOutputModel(Long id, BigDecimal amount, LocalDate transactionDate, IncomeOutputModel income,
                                           CategoryUserOutput categoryUserOutput) {
}
