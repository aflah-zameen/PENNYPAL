package com.application.pennypal.interfaces.rest.dtos.income;

import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeTransactionResponseDTO(Long id, String title, BigDecimal amount, LocalDate transactionDate,
                                           CategoryUserResponseDTO category) {
}
