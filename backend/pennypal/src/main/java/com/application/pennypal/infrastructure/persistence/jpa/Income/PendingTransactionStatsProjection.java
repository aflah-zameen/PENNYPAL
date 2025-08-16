package com.application.pennypal.infrastructure.persistence.jpa.Income;

import java.math.BigDecimal;

public interface PendingTransactionStatsProjection {
    BigDecimal getTotalAmount();
    Long getCount();
}
