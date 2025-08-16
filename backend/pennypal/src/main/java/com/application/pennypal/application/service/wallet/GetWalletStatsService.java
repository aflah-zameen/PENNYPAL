package com.application.pennypal.application.service.wallet;

import com.application.pennypal.application.dto.output.wallet.WalletStatsOutputModel;
import com.application.pennypal.application.port.in.wallet.GetWalletStats;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetWalletStatsService implements GetWalletStats {
    private final TransactionRepositoryPort transactionRepositoryPort;

    @Override
    public WalletStatsOutputModel execute(String userId) {
        return transactionRepositoryPort.getWalletStats(userId);
    }
}
