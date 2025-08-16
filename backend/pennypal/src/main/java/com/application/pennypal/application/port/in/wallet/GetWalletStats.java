package com.application.pennypal.application.port.in.wallet;

import com.application.pennypal.application.dto.output.wallet.WalletStatsOutputModel;

public interface GetWalletStats {
    WalletStatsOutputModel execute(String userId);
}
