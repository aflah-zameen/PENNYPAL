package com.application.pennypal.infrastructure.adapter.out.coin;

import com.application.pennypal.application.port.out.repository.UserCoinAccountRepositoryPort;
import com.application.pennypal.domain.coin.UserCoinAccount;
import com.application.pennypal.infrastructure.persistence.jpa.coin.entity.UserCoinAccountEntity;
import com.application.pennypal.infrastructure.persistence.jpa.coin.repository.UserCoinAccountRepository;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.CoinJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCoinAccountRepositoryAdapter implements UserCoinAccountRepositoryPort {
    private final UserCoinAccountRepository userCoinAccountRepository;
    @Override
    public Optional<UserCoinAccount> findByUserId(String userId) {
        return userCoinAccountRepository.findById(userId).map(CoinJpaMapper::toDomain);
    }

    @Override
    public BigDecimal getCoins(String userId) {
        return userCoinAccountRepository.findBalanceByUserId(userId)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public UserCoinAccount save(UserCoinAccount account) {
        UserCoinAccountEntity entity = userCoinAccountRepository.save(CoinJpaMapper.toEntity(account));
        return CoinJpaMapper.toDomain(entity);
    }
}
