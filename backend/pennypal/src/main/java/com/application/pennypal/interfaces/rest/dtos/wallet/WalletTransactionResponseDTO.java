package com.application.pennypal.interfaces.rest.dtos.wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletTransactionResponseDTO(
        String id,
        String type,
        BigDecimal amount,
        String description,
        LocalDateTime timestamp,
        String category,
        String icon
) {
}
