package com.application.pennypal.domain.user.entity;

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
    private String name;
    private Category category;
    private BigDecimal amount;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private boolean active;
}
