package com.application.pennypal.application.service.wallet;

import com.application.pennypal.application.dto.input.wallet.AddWalletInputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.wallet.AddMoneyToWallet;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.WalletRepositoryPort;
import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.domain.card.entity.Card;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.domain.wallet.entity.Wallet;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class AddMoneyToWalletService implements AddMoneyToWallet {
    private final WalletRepositoryPort walletRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final EncodePasswordPort encodePasswordPort;
    private final CardRepositoryPort cardRepositoryPort;
    @Override
    public void execute(String userId,AddWalletInputModel inputModel) {
        Card card = cardRepositoryPort.findByUserIdAndCardId(userId,inputModel.cardId())
                .orElseThrow(() -> new ApplicationBusinessException("Card not Found","NOT_FOUND"));
        if(!encodePasswordPort.matches(inputModel.pin(),card.getHashedPin())){
            throw new ApplicationBusinessException("Invalid Card Pin","INVALID_CARD_PIN");
        }

        Wallet wallet = walletRepositoryPort.findByUserId(userId)
                        .orElseThrow(() -> new ApplicationBusinessException("Wallet not Found","NOT_FOUND"));

        card = card.debitAmount(inputModel.amount());
        wallet = wallet.creditAmount(inputModel.amount());

        /// Create Transaction
        Transaction transaction = Transaction.create(
                wallet.getUserId(),
                null,
                inputModel.cardId(),
                null,
                inputModel.amount(),
                TransactionType.WALLET,
                "WALLET TRANSFER",
                inputModel.notes(),
                PaymentMethod.CARD,
                LocalDate.now(),
                false,
                null,
                wallet.getUserId(),
                null,
                null
        );

        cardRepositoryPort.update(card);
        walletRepositoryPort.update(wallet);
        transactionRepositoryPort.save(transaction);
    }
}
