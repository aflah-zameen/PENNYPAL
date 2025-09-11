package com.application.pennypal.application.dto.output.lend;

import java.math.BigDecimal;

public record LoanRepaymentOutputModel(
        BigDecimal coins
) {
}
