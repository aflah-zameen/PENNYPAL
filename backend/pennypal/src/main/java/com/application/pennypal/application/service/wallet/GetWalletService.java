package com.application.pennypal.application.service.wallet;

import com.application.pennypal.application.dto.output.wallet.WalletOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.wallet.GetWallet;
import com.application.pennypal.application.port.out.repository.WalletRepositoryPort;
import com.application.pennypal.domain.wallet.entity.Wallet;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetWalletService implements GetWallet {

    private final WalletRepositoryPort walletRepositoryPort;
    @Override
    public WalletOutputModel execute(String userId) {
        Wallet wallet = walletRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new ApplicationBusinessException("Wallet not Found","NOT_FOUND"));
        return new WalletOutputModel(
                wallet.getWalletId(),
                wallet.getUserId(),
                wallet.getBalanceAmount(),
                wallet.getUpdatedAt()
        );
    }
}
