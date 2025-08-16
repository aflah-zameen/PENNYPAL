package com.application.pennypal.application.dto.output.card;

import java.math.BigDecimal;
import java.util.List;

public record CardSpendingOutputModel(List<String> label, List<BigDecimal> amount) {
}
