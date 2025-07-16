package com.application.pennypal.application.output;

import java.math.BigDecimal;

public record FinancialProgressOutput(BigDecimal currentAmount, Double progress) {
}
