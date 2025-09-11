package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.domain.coin.CoinTransaction;

import java.util.List;

public interface CoinTransactionRepositoryPort {
    CoinTransaction save(CoinTransaction transaction);
    List<CoinTransaction> getAllByUserId(String userId);
    List<CoinTransaction> getAll();
}
