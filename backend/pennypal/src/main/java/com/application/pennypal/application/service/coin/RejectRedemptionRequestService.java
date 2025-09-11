package com.application.pennypal.application.service.coin;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.coin.RejectRedemptionRequest;
import com.application.pennypal.application.port.out.repository.RedemptionRequestRepositoryPort;
import com.application.pennypal.domain.coin.RedemptionRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class RejectRedemptionRequestService implements RejectRedemptionRequest {
    private final RedemptionRequestRepositoryPort repositoryPort;
    @Override
    public void execute(String userId, String redemptionId) {
        RedemptionRequest redemptionRequest = repositoryPort.findById(redemptionId)
                .orElseThrow(() -> new ApplicationBusinessException("Redemption request not found","NOT_FOUND"));

        redemptionRequest.reject(userId, LocalDateTime.now());
        repositoryPort.save(redemptionRequest);
    }
}
