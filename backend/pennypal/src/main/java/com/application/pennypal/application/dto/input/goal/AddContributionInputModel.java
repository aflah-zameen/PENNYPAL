package com.application.pennypal.application.dto.input.goal;

import java.math.BigDecimal;

public record AddContributionInputModel(String userId, String goalId,String cardId, BigDecimal amount, String notes) {
}
