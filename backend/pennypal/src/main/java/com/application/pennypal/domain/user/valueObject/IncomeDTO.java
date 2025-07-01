package com.application.pennypal.domain.user.valueObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record IncomeDTO(BigDecimal amount, String source, LocalDate income_date,
                        String status,LocalDateTime createdAt,String notes) {
}
