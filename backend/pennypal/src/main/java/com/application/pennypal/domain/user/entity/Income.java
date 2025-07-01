package com.application.pennypal.domain.user.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Income {
    private Long id;
    private BigDecimal amount;
    private String source;
    private LocalDate income_date;
    private String status;
    private LocalDateTime createdAt;
}
