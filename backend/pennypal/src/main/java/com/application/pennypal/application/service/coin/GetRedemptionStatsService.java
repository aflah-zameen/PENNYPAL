package com.application.pennypal.application.service.coin;

import com.application.pennypal.application.dto.output.coin.RedemptionStatsOutputModel;
import com.application.pennypal.application.port.in.coin.GetRedemptionStats;
import com.application.pennypal.application.port.out.repository.RedemptionRequestRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetRedemptionStatsService implements GetRedemptionStats {
    private final RedemptionRequestRepositoryPort redemptionRequestRepositoryPort;
    @Override
    public RedemptionStatsOutputModel execute() {
        return redemptionRequestRepositoryPort.getStats();
    }
}
