package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.input.transaction.TransferInputModel;
import com.application.pennypal.application.dto.output.user.TransferTransaction;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.transaction.TransferMoney;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.domain.card.entity.Card;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class TransferMoneyService implements TransferMoney {
    private final CardRepositoryPort cardRepositoryPort;
    private final EncodePasswordPort encodePasswordPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public TransferTransaction execute(TransferInputModel inputModel) {
        try{
            Card senderCard = cardRepositoryPort.findByCardId(inputModel.methodId())
                    .orElseThrow(() -> new ApplicationBusinessException("Card cannot be found","NOT_FOUND"));

            /// Validate pin
            if(encodePasswordPort.matches(inputModel.pin(),senderCard.getHashedPin())){
                Card receiverCard = cardRepositoryPort.getAllCards(inputModel.recipientId()).getFirst();
                senderCard = senderCard.debitAmount(inputModel.amount());
                receiverCard = receiverCard.creditAmount(inputModel.amount());
                cardRepositoryPort.update(senderCard);
                cardRepositoryPort.update(receiverCard);

                Transaction transaction = Transaction.create(
                        inputModel.senderId(),
                        null,
                        inputModel.methodId(),
                        null,
                        inputModel.amount(),
                        TransactionType.TRANSFER,
                        "Transfer",
                        inputModel.notes(),
                        PaymentMethod.CARD,
                        LocalDate.now(),
                        false,
                        null,
                        inputModel.recipientId(),
                        inputModel.senderId(),
                        receiverCard.getCardId()
                );

                Transaction newTransaction = transactionRepositoryPort.save(transaction);

                return new TransferTransaction(
                        newTransaction.getTransactionId(),
                        newTransaction.getTransferFromUserId().orElse(""),
                        newTransaction.getTransferToUserId().orElse(""),
                        newTransaction.getAmount(),
                        newTransaction.getDescription(),
                        newTransaction.getTransactionDate(),
                        newTransaction.getTransactionStatus().getValue(),
                        "",
                        "sent"

                );

            }
            else {
                throw new ApplicationBusinessException("Invalid Card Pin","INVALID_CARD_PIN");
            }        }
        catch (NoSuchElementException ex){
            throw new ApplicationBusinessException("Receiver has no card to receive money","NOT_VALID");
        }

    }
}
