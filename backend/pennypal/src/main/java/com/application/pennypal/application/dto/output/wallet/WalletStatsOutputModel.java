package com.application.pennypal.application.dto.output.wallet;

import java.math.BigDecimal;

public record WalletStatsOutputModel(
        long totalTransactions,
        BigDecimal averageTransaction
) {
}
