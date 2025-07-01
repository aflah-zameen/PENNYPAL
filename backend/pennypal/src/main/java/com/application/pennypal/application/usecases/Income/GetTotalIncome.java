package com.application.pennypal.application.usecases.Income;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface GetTotalIncome {
    BigDecimal get(Long userId, LocalDate currentDate);
}
