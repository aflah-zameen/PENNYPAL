package com.application.pennypal.domain.entity;

import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.domain.valueObject.RecurringStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class Expense {
    private Long id;
    private Long userId;
    private String title;
    private BigDecimal amount;
    private Long categoryId;
    private LocalDate expenseDate;
    private RecurringStatus status;
    private String description;

    private boolean isRecurring;
    private LocalDate startDate;
    private LocalDate endDate;
    private RecurrenceFrequency frequency;
    private boolean recurrenceActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;

    public void deleteExpense(){
        this.deleted = true;
    }
    public void toggle(){
        this.recurrenceActive = !this.recurrenceActive;
    }

}
