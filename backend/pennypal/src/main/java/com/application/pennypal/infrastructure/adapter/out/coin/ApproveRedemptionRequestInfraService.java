package com.application.pennypal.infrastructure.adapter.out.coin;

import com.application.pennypal.application.port.in.coin.ApproveRedemptionRequest;
import com.application.pennypal.application.port.out.repository.RedemptionRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.WalletRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.coin.RedemptionRequest;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.domain.wallet.entity.Wallet;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ApproveRedemptionRequestInfraService {
    private final ApproveRedemptionRequest approveRedemptionRequest;
    private final WalletRepositoryPort walletRepositoryPort;
    private final MessageBrokerPort  messageBrokerPort;
    private final RedemptionRequestRepositoryPort redemptionRequestRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Transactional
    public void approve(String userId,String redemptionId){
        RedemptionRequest redemptionRequest = redemptionRequestRepositoryPort.findById(redemptionId)
                .orElseThrow(() -> new InfrastructureException("Redemption not found","NOT_FOUND"));
        BigDecimal money = approveRedemptionRequest.execute(userId,redemptionId);
        Wallet wallet = walletRepositoryPort.findByUserId(redemptionRequest.getUserId())
                .orElseThrow(() -> new InfrastructureException("Wallet not found","NOT_FOUND"));

        /// Transfer amount
        wallet  = wallet.creditAmount(money);
        walletRepositoryPort.update(wallet);

        Transaction transaction = Transaction.create(
                redemptionRequest.getUserId(),
                null,
                null,
                null,
                money,
                TransactionType.WALLET,
                "COIN REWARD",
                null,
                PaymentMethod.ADMIN_CARD,
                LocalDate.now(),
                false,
                null,null,null,null
        );
        transactionRepositoryPort.save(transaction);

        String message = String.format(
                "🎉 Congratulations! An amount of $%.2f has been added to your wallet as part of your coin redemption. Thank you for using PennyPal!",
                money
        );
        Notification notification = Notification.create(
                redemptionRequest.getUserId(),
                message,
                null,
                null,
                false
        );


        messageBrokerPort.notifyPrivateUser(notification,redemptionRequest.getUserId());

    }
}
