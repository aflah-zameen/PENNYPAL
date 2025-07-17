package com.application.pennypal.interfaces.rest.dtos.Expense;

import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PendingExpenseResponseDTO(Long incomeId, String title,
                                        BigDecimal amount, LocalDate expenseDate,
                                        CategoryUserResponseDTO category) {
}
