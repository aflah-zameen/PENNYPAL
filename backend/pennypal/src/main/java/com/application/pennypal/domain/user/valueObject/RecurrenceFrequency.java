package com.application.pennypal.domain.user.valueObject;

import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

@Getter
public enum RecurrenceFrequency {
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY");
    private final String value;
    RecurrenceFrequency(String value){
        this.value = value;
    }
    public int getOccurrencesThisMonth(LocalDate start) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate end = currentMonth.atEndOfMonth();

        return switch (this) {
            case DAILY -> Period.between(start, end).getDays() + 1;
            case WEEKLY -> (int) ChronoUnit.WEEKS.between(start, end) + 1;
            case MONTHLY -> 1;
        };
    }
}
