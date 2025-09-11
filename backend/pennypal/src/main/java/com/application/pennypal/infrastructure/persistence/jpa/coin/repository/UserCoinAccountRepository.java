package com.application.pennypal.infrastructure.persistence.jpa.coin.repository;

import com.application.pennypal.infrastructure.persistence.jpa.coin.entity.UserCoinAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserCoinAccountRepository extends JpaRepository<UserCoinAccountEntity,String> {

    @Query("SELECT r.balance FROM UserCoinAccountEntity r "+
    "WHERE r.userId = :userId")
    Optional<BigDecimal> findBalanceByUserId(@Param("userId") String userId);
}
