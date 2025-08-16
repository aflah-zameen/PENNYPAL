package com.application.pennypal.interfaces.rest.dtos.income;

import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeRecurringTransactionResponseDTO(String recurringId, String categoryId, CategoryUserResponseDTO category,
                                                    String title, String description, BigDecimal amount, LocalDate startDate, LocalDate endDate,
                                                    boolean active,boolean deleted
                                                    ) { }
