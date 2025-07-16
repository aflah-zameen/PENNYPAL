package com.application.pennypal.application.output.income;

import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RecurringIncomeOutput(Long id, boolean active, BigDecimal amount, LocalDate startDate, LocalDate endDate, String title, Boolean recurrence,
                                    RecurrenceFrequency frequency, CategoryUserResponseDTO category){
}

