package com.application.pennypal.application.dto.input.coin;

import com.application.pennypal.domain.coin.RedemptionRequestStatus;

import java.time.LocalDateTime;

public record RedemptionFilterInputModel(
        int page,
        int size,
        String status,
//        String search,
        LocalDateTime dateFrom,
        LocalDateTime dateTo
) {
}
