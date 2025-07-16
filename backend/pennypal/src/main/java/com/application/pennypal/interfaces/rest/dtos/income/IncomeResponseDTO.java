package com.application.pennypal.interfaces.rest.dtos.income;

import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record IncomeResponseDTO(Long id, BigDecimal amount, String title, CategoryUserResponseDTO category,
                                LocalDate incomeDate, String status, String description, Boolean isRecurring,
                                String frequency, LocalDate startDate, LocalDate endDate, LocalDateTime updatedAt,
                                LocalDateTime createdAt, Boolean recurrenceActive, Boolean deleted
                             ) {
}
