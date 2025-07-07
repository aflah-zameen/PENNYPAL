package com.application.pennypal.domain.user.entity;

import com.application.pennypal.domain.user.valueObject.RecurrenceFrequency;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Income {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private Category source;
    private LocalDate income_date;
    private String status;
    private LocalDateTime createdAt;
    private String notes;
    private Boolean recurrence;
    private RecurrenceFrequency frequency;
    private boolean recurrenceActive;

    public void toggle(){
        this.recurrenceActive = !this.recurrenceActive;
    }
}
