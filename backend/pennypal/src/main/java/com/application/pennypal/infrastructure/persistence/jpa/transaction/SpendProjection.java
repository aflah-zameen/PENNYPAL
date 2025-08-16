package com.application.pennypal.infrastructure.persistence.jpa.transaction;

import java.math.BigDecimal;

public interface SpendProjection {
    Integer getLabel();
    BigDecimal getTotal();
}
