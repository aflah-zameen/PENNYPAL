package com.application.pennypal.application.dto.output.income;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeTransactionOutputModel(Long id, BigDecimal amount, LocalDate transactionDate, IncomeOutputModel income,
                                           CategoryUserOutput categoryUserOutput) {
}
