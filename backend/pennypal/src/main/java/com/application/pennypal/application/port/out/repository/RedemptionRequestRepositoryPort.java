package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.input.coin.RedemptionFilterInputModel;
import com.application.pennypal.application.dto.output.coin.PaginatedRedemptionRequest;
import com.application.pennypal.application.dto.output.coin.RedemptionHistoryOutputModel;
import com.application.pennypal.application.dto.output.coin.RedemptionStatsOutputModel;
import com.application.pennypal.domain.coin.RedemptionRequest;

import java.util.List;
import java.util.Optional;

public interface RedemptionRequestRepositoryPort {
    List<RedemptionRequest> getHistory(String userId);

    RedemptionRequest save(RedemptionRequest redemptionRequest);

    RedemptionStatsOutputModel getStats();
    

    Optional<RedemptionRequest> findById(String redemptionId);

    PaginatedRedemptionRequest getAll(RedemptionFilterInputModel inputModel);
}
