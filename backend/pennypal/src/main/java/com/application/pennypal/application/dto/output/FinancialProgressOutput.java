package com.application.pennypal.application.dto.output;

import java.math.BigDecimal;

public record FinancialProgressOutput(BigDecimal currentAmount, Double progress) {
}
