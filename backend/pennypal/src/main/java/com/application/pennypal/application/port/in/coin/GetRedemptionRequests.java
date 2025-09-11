package com.application.pennypal.application.port.in.coin;

import com.application.pennypal.application.dto.input.coin.RedemptionFilterInputModel;
import com.application.pennypal.application.dto.output.coin.PaginatedRedemptionRequest;
import com.application.pennypal.application.dto.output.coin.PaginationRedemptionInfo;
import com.application.pennypal.application.dto.output.coin.RedemptionHistoryOutputModel;

import java.util.List;

public interface GetRedemptionRequests {
    PaginatedRedemptionRequest execute(RedemptionFilterInputModel inputModel);
}
