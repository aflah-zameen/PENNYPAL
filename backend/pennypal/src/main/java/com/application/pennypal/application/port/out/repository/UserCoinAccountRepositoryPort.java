package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.domain.coin.UserCoinAccount;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserCoinAccountRepositoryPort {
    Optional<UserCoinAccount> findByUserId(String userId);
    BigDecimal getCoins(String userId);
    UserCoinAccount save(UserCoinAccount account);
}
