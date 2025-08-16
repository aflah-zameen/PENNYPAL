package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.wallet.WalletStatsOutputModel;
import com.application.pennypal.domain.wallet.entity.Wallet;

import java.util.Optional;

public interface WalletRepositoryPort {
    void save(Wallet wallet);
    Optional<Wallet> findByUserId(String userId);
    void update(Wallet wallet);

}
