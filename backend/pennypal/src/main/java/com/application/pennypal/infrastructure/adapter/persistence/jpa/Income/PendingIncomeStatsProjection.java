package com.application.pennypal.infrastructure.adapter.persistence.jpa.Income;

import java.math.BigDecimal;

public interface PendingIncomeStatsProjection {
    BigDecimal getTotalAmount();
    Long getCount();
}
