package com.application.pennypal.application.port.in.coin;

import com.application.pennypal.application.dto.output.coin.RedemptionStatsOutputModel;

public interface GetRedemptionStats {
    RedemptionStatsOutputModel execute();
}
