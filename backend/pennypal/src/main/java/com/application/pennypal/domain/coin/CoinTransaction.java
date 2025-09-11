package com.application.pennypal.domain.coin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CoinTransaction {
    private final String id;
    private final String userId;
    private final CoinTransactionType transactionType;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;

}
