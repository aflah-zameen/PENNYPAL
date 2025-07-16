package com.application.pennypal.interfaces.rest.dtos.income;

import java.math.BigDecimal;

public record TotalIncomeSummaryDTO(BigDecimal totalAmount, Double progressValue) {
}
