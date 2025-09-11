package com.application.pennypal.application.service.coin;

import com.application.pennypal.application.port.in.coin.CoinReward;
import com.application.pennypal.application.port.out.repository.CoinTransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.RewardPolicyRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserCoinAccountRepositoryPort;
import com.application.pennypal.domain.coin.CoinTransaction;
import com.application.pennypal.domain.coin.CoinTransactionType;
import com.application.pennypal.domain.coin.UserCoinAccount;
import com.application.pennypal.domain.reward.RewardActionType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class CoinRewardService implements CoinReward {
    private final RewardPolicyRepositoryPort rewardPolicyRepositoryPort;
    private final UserCoinAccountRepositoryPort userCoinAccountRepositoryPort;
    private final CoinTransactionRepositoryPort coinTransactionRepositoryPort;
    @Override
    public BigDecimal addCoinsForGoal(String userId, String goalId) {
        BigDecimal coins = rewardPolicyRepositoryPort.getRewardAmount(RewardActionType.GOAL_COMPLETION);
        applyReward(userId,coins,CoinTransactionType.GOAL_COMPLETION);
        return coins;
    }

    @Override
    public BigDecimal addCoinsForLoanRepayment(String userId, String LoanId) {
        BigDecimal coins = rewardPolicyRepositoryPort.getRewardAmount(RewardActionType.LOAN_REPAYMENT);
        applyReward(userId,coins,CoinTransactionType.LOAN_REPAYMENT);
        return coins;
    }

    private void applyReward(String userId, BigDecimal coins, CoinTransactionType type) {
        if (coins.compareTo(BigDecimal.ZERO) <= 0) return;

        UserCoinAccount account = userCoinAccountRepositoryPort.findByUserId(userId)
                .orElse(UserCoinAccount.create(userId,BigDecimal.ZERO,BigDecimal.ZERO));

        account.addCoins(coins);
        userCoinAccountRepositoryPort.save(account);

        CoinTransaction transaction = CoinTransaction.builder()
                .id("COIN_TRA"+UUID.randomUUID())
                .userId(userId)
                .transactionType(type)
                .amount(coins)
                .timestamp(LocalDateTime.now())
                .build();

        coinTransactionRepositoryPort.save(transaction);
    }
}
