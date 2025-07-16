package com.application.pennypal.application.usecases.goal;

import java.math.BigDecimal;

public interface AddContribution {
    void execute(Long userId,Long goalId, BigDecimal amount, String notes);
}
