package com.application.pennypal.application.dto.output.coin;

import java.math.BigDecimal;

public record CoinBalanceOutputModel(
        BigDecimal availableCoins,
        BigDecimal totalCoins
) {
}
