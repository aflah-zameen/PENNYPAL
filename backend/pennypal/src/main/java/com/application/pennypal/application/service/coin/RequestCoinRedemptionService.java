package com.application.pennypal.application.service.coin;

import com.application.pennypal.application.dto.output.coin.RedemptionHistoryOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.coin.RequestCoinRedemption;
import com.application.pennypal.application.port.out.repository.RedemptionRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserCoinAccountRepositoryPort;
import com.application.pennypal.domain.coin.RedemptionRequest;
import com.application.pennypal.domain.coin.RedemptionRequestStatus;
import com.application.pennypal.domain.coin.UserCoinAccount;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class RequestCoinRedemptionService implements RequestCoinRedemption {
    private final RedemptionRequestRepositoryPort redemptionRequestRepositoryPort;
    private final UserCoinAccountRepositoryPort userCoinAccountRepositoryPort;
    @Override
    public RedemptionHistoryOutputModel execute(String userId,BigDecimal coinAmount, BigDecimal realMoney) {

        RedemptionRequest redemptionRequest = RedemptionRequest.builder()
                .id("RED_"+ UUID.randomUUID())
                .userId(userId)
                .coinsRedeemed(coinAmount)
                .realMoney(realMoney)
                .status(RedemptionRequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        redemptionRequest = redemptionRequestRepositoryPort.save(redemptionRequest);

        /// update user coin account
        UserCoinAccount coinAccount = userCoinAccountRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new ApplicationBusinessException("Coin account cannot be found","NOT_FOUND"));

        coinAccount.subtractCoins(coinAmount);
        userCoinAccountRepositoryPort.save(coinAccount);
        
        return new RedemptionHistoryOutputModel(
                redemptionRequest.getId(),
                redemptionRequest.getCoinsRedeemed(),
                redemptionRequest.getRealMoney(),
                redemptionRequest.getStatus(),
                redemptionRequest.getCreatedAt(),
                redemptionRequest.getApprovedAt()
        );
    }
}
