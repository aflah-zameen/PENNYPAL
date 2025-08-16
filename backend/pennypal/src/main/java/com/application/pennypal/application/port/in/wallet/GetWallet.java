package com.application.pennypal.application.port.in.wallet;

import com.application.pennypal.application.dto.output.wallet.WalletOutputModel;
import com.application.pennypal.application.dto.output.wallet.WalletStatsOutputModel;

public interface GetWallet {
    WalletOutputModel execute(String userId);
}
