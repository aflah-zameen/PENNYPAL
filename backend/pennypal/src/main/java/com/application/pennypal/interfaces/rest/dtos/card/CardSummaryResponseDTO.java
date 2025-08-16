package com.application.pennypal.interfaces.rest.dtos.card;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CardSummaryResponseDTO(String id,
                                     String number,
                                     String name,
                                     String expiry,
                                     String holder,
                                     String type,
                                     BigDecimal balance,
                                     BigDecimal income,
                                     BigDecimal expense,
                                     String gradient,
                                     boolean active,
                                     LocalDateTime createdAt) {
}
