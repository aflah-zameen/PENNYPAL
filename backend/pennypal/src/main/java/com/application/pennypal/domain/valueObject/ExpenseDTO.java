package com.application.pennypal.domain.valueObject;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseDTO(String name, BigDecimal amount, Long category, String type, LocalDate startDate,
                         LocalDate endDate) {
}
