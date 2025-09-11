package com.application.pennypal.infrastructure.persistence.jpa.coin.repository;

import com.application.pennypal.domain.coin.CoinTransaction;
import com.application.pennypal.infrastructure.persistence.jpa.coin.entity.CoinTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinTransactionRepository extends JpaRepository<CoinTransactionEntity,String> {
    List<CoinTransactionEntity> findAllByUserIdOrderByTimestampDesc(String userId);
    List<CoinTransactionEntity> findAllByOrderByTimestampDesc();
}
