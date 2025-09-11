package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.coin.CoinTransaction;
import com.application.pennypal.domain.coin.RedemptionRequest;
import com.application.pennypal.domain.coin.UserCoinAccount;
import com.application.pennypal.infrastructure.persistence.jpa.coin.entity.CoinTransactionEntity;
import com.application.pennypal.infrastructure.persistence.jpa.coin.entity.RedemptionRequestEntity;
import com.application.pennypal.infrastructure.persistence.jpa.coin.entity.UserCoinAccountEntity;

public class CoinJpaMapper {
    public static CoinTransaction toDomain(CoinTransactionEntity entity){
        return CoinTransaction.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .transactionType(entity.getTransactionType())
                .timestamp(entity.getTimestamp())
                .build();
    }

    public static CoinTransactionEntity toEntity(CoinTransaction domain){
        return CoinTransactionEntity.builder()
                .id(domain.getId())
                .amount(domain.getAmount())
                .timestamp(domain.getTimestamp())
                .transactionType(domain.getTransactionType())
                .userId(domain.getUserId())
                .build();
    }

    public static UserCoinAccount toDomain(UserCoinAccountEntity entity) {
        return UserCoinAccount.reconstruct(
                entity.getUserId(),
                entity.getBalance(),
                entity.getTotalEarned(),
                entity.getLastUpdated()
        );
    }

    public static UserCoinAccountEntity toEntity(UserCoinAccount domain) {
        return UserCoinAccountEntity.builder()
                .userId(domain.getUserId())
                .balance(domain.getBalance())
                .totalEarned(domain.getTotalEarned())
                .build();
    }


    public static RedemptionRequest toDomain(RedemptionRequestEntity entity){
        return RedemptionRequest.builder()
                .id(entity.getId())
                .coinsRedeemed(entity.getCoinsRedeemed())
                .realMoney(entity.getRealMoney())
                .approvedAt(entity.getApprovedAt())
                .approvedBy(entity.getApprovedBy())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .userId(entity.getUserId())
                .build();
    }

    public static RedemptionRequestEntity toEntity(RedemptionRequest domain){
        return RedemptionRequestEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .coinsRedeemed(domain.getCoinsRedeemed())
                .realMoney(domain.getRealMoney())
                .approvedAt(domain.getApprovedAt())
                .approvedBy(domain.getApprovedBy())
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .build();
    }


}
