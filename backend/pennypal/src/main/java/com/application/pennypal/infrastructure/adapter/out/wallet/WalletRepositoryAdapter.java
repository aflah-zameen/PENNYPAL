package com.application.pennypal.infrastructure.adapter.out.wallet;

import com.application.pennypal.application.dto.output.wallet.WalletStatsOutputModel;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.repository.WalletRepositoryPort;
import com.application.pennypal.domain.wallet.entity.Wallet;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.WalletEntity;
import com.application.pennypal.infrastructure.persistence.jpa.user.SpringDataUserRepository;
import com.application.pennypal.infrastructure.persistence.jpa.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WalletRepositoryAdapter implements WalletRepositoryPort {
    private final WalletRepository walletRepository;
    private final SpringDataUserRepository userRepository;
    @Override
    public void save(Wallet wallet) {
        UserEntity user = userRepository.findByUserId(wallet.getUserId())
                        .orElseThrow(() -> new InfrastructureException("User not found","NOT_FOUND"));
        walletRepository.save(new WalletEntity(
                wallet.getWalletId(),
                user,
                wallet.getBalanceAmount(),
                wallet.isActive()
        ));
    }

    @Override
    public Optional<Wallet> findByUserId(String userId) {
        return walletRepository.findByUser_UserId(userId).map(walletEntity ->
                Wallet.reconstruct(
                        walletEntity.getWalletId(),
                        walletEntity.getUser().getUserId(),
                        walletEntity.getBalanceAmount(),
                        walletEntity.isActive(),
                        walletEntity.getCreatedAt(),
                        walletEntity.getUpdatedAt()
                ));
    }

    @Override
    public void update(Wallet wallet) {
        WalletEntity entity = walletRepository.findByUser_UserId(wallet.getUserId())
                .orElseThrow(() -> new InfrastructureException("Wallet not found","NOT_FOUND"));
        entity.setBalanceAmount(wallet.getBalanceAmount());
        walletRepository.save(entity);
    }
}
