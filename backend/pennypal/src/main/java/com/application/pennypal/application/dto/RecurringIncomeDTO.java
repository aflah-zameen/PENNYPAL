package com.application.pennypal.application.dto;

import com.application.pennypal.domain.user.entity.Category;
import com.application.pennypal.domain.user.valueObject.RecurrenceFrequency;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RecurringIncomeDTO(Long id, boolean active, BigDecimal amount, LocalDate incomeDate, String source, Boolean recurrence,
                                 RecurrenceFrequency frequency){
}
