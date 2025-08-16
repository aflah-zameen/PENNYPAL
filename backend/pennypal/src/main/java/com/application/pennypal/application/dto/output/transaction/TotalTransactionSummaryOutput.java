package com.application.pennypal.application.dto.output.transaction;

import java.math.BigDecimal;

public record TotalTransactionSummaryOutput(BigDecimal totalAmount, Double progressValue) {
}
