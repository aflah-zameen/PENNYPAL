package com.application.pennypal.application.dto.input.card;

import java.time.LocalDate;

public record ExpenseFilterInputModel(
        String range,
        LocalDate fromDate,
        LocalDate toDate
) {
}
