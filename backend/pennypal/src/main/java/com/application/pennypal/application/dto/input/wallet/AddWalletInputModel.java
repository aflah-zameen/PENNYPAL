package com.application.pennypal.application.dto.input.wallet;
import java.math.BigDecimal;

public record AddWalletInputModel(
        String cardId,
        String pin,
        BigDecimal amount,
        String notes
) {
}
