package com.application.pennypal.infrastructure.adapter.out.coin;

import com.application.pennypal.application.port.out.repository.CoinTransactionRepositoryPort;
import com.application.pennypal.domain.coin.CoinTransaction;
import com.application.pennypal.infrastructure.persistence.jpa.coin.entity.CoinTransactionEntity;
import com.application.pennypal.infrastructure.persistence.jpa.coin.repository.CoinTransactionRepository;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.CoinJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CoinTransactionRepositoryAdapter implements CoinTransactionRepositoryPort {
    private final CoinTransactionRepository coinTransactionRepository;
    @Override
    public CoinTransaction save(CoinTransaction transaction) {
        CoinTransactionEntity entity = coinTransactionRepository.save(CoinJpaMapper.toEntity(transaction));
        return CoinJpaMapper.toDomain(entity);
    }

    @Override
    public List<CoinTransaction> getAllByUserId(String userId) {
        return coinTransactionRepository.findAllByUserIdOrderByTimestampDesc(userId).stream()
                .map(CoinJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<CoinTransaction> getAll() {
        return coinTransactionRepository.findAllByOrderByTimestampDesc().stream()
                .map(CoinJpaMapper::toDomain)
                .toList();
    }
}
