package com.application.pennypal.interfaces.rest.dtos.transaction;

import com.application.pennypal.interfaces.rest.dtos.card.CardUserResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.user.UserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record RecurringTransactionResponseDTO(String recurringId,
                                              String userId,
                                              CardUserResponseDTO card,
                                              CategoryUserResponseDTO category,
                                              String transactionType,
                                              String title,
                                              String description,
                                              BigDecimal amount,
                                              String frequency,
                                              LocalDate startDate,
                                              LocalDate endDate,
                                              LocalDate lastGeneratedDate,
                                              boolean active,
                                              LocalDateTime createdAt) {
}
