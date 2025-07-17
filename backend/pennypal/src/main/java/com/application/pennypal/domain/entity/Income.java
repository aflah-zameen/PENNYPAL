package com.application.pennypal.domain.entity;

import com.application.pennypal.domain.valueObject.RecurringStatus;
import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Income {
    private Long id;
    private Long userId;
    private String title;
    private BigDecimal amount;
    private Long categoryId;
    private LocalDate incomeDate;
    private RecurringStatus status;
    private String description;

    private Boolean isRecurring;
    private LocalDate startDate;
    private LocalDate endDate;
    private RecurrenceFrequency frequency;
    private boolean recurrenceActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
    public void toggle(){
        this.recurrenceActive = !this.recurrenceActive;
    }
}
