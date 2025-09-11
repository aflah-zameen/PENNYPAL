package com.application.pennypal.application.service.coin;

import com.application.pennypal.application.dto.input.coin.RedemptionFilterInputModel;
import com.application.pennypal.application.dto.output.coin.PaginatedRedemptionRequest;
import com.application.pennypal.application.port.in.coin.GetRedemptionRequests;
import com.application.pennypal.application.port.out.repository.RedemptionRequestRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetRedemptionRequestsService implements GetRedemptionRequests {
    private final RedemptionRequestRepositoryPort redemptionRequestRepositoryPort;
    @Override
    public PaginatedRedemptionRequest execute(RedemptionFilterInputModel inputModel) {
        return redemptionRequestRepositoryPort.getAll(inputModel);
    }
}
