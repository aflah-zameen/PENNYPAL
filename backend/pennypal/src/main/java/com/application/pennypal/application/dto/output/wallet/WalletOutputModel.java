package com.application.pennypal.application.dto.output.wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletOutputModel(
        String id,
        String userId,
        BigDecimal balance,
        LocalDateTime lastUpdated
) {
}
