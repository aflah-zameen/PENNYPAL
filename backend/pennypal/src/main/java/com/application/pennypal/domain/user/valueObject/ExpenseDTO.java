package com.application.pennypal.domain.user.valueObject;

import com.application.pennypal.domain.user.entity.Category;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseDTO(String name, BigDecimal amount, Long category, String type, LocalDate startDate,
                         LocalDate endDate) {
}
