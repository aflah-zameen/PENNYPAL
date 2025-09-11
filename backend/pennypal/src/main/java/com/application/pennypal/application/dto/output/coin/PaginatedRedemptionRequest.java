package com.application.pennypal.application.dto.output.coin;

import com.application.pennypal.interfaces.rest.dtos.coin.AdminRedemptionResponse;

import java.util.List;

public record PaginatedRedemptionRequest(
        List<AdminRedemptionResponse> requests,
        PaginationRedemptionInfo pagination
) {
}
