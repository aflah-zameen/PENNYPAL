package com.application.pennypal.application.service.coin;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.coin.ApproveRedemptionRequest;
import com.application.pennypal.application.port.out.repository.RedemptionRequestRepositoryPort;
import com.application.pennypal.domain.coin.RedemptionRequest;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ApproveRedemptionRequestService implements ApproveRedemptionRequest {
    private final RedemptionRequestRepositoryPort redemptionRequestRepositoryPort;
    @Override
    public BigDecimal execute(String userId, String redemptionId) {
        RedemptionRequest redemptionRequest = redemptionRequestRepositoryPort.findById(redemptionId)
                .orElseThrow(() -> new ApplicationBusinessException("Redemption request not found","NOT_FOUND"));

        redemptionRequest.approve(userId, LocalDateTime.now());
        redemptionRequest = redemptionRequestRepositoryPort.save(redemptionRequest);
        return redemptionRequest.getRealMoney();
    }
}
