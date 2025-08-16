package com.application.pennypal.infrastructure.persistence.jpa.wallet;

import com.application.pennypal.domain.wallet.entity.Wallet;
import com.application.pennypal.infrastructure.persistence.jpa.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity,Long> {
    Optional<WalletEntity> findByUser_UserId(String userId);
}
