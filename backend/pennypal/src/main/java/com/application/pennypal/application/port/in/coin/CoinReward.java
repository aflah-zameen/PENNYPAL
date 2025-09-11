package com.application.pennypal.application.port.in.coin;

import java.math.BigDecimal;

public interface CoinReward {
    BigDecimal addCoinsForGoal(String userId, String goalId);
    BigDecimal addCoinsForLoanRepayment(String userId , String goalId);
}
