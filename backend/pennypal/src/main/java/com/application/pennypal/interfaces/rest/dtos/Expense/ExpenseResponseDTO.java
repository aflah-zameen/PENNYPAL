package com.application.pennypal.interfaces.rest.dtos.Expense;

import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponseDTO(Long id, String name, CategoryUserResponseDTO category,
                                 BigDecimal amount, LocalDate startDate,LocalDate endDate,String type) {

}
