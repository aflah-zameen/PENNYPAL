package com.application.pennypal.application.dto.output.lend;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RepayTransactionOutputModel(String id,
                                          String senderId,
                                          String recipientId,
                                          BigDecimal amount,
                                          String note,
                                          LocalDate date,
                                          String status,
                                          String failureReason,
                                          String type,
                                          BigDecimal coins) {
}
