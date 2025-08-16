package com.application.pennypal.interfaces.rest.dtos.transaction;

import java.math.BigDecimal;

public record AddContributionDTO(String userId,String categoryId, String goalId,String cardId, BigDecimal amount, String notes) {
}
