package com.application.pennypal.application.mappers.transaction;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.card.CardApplicationMapper;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import com.application.pennypal.domain.card.entity.Card;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.exception.category.CategoryRequiredExceptionDomain;
import com.application.pennypal.domain.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionApplicationMapper {
    private final CategoryManagementRepositoryPort categoryManagementRepositoryPort;
    private final CardRepositoryPort cardRepositoryPort;

    public TransactionOutputModel toOutput(Transaction transaction){
        Category category = null;
        if(transaction.getCategoryId().isPresent()){
           category = categoryManagementRepositoryPort.findByCategoryId(transaction.getCategoryId().get())
                    .orElseThrow(CategoryRequiredExceptionDomain::new);
        }
        Card card = null;
        if(transaction.getCardId().isPresent())
            card = cardRepositoryPort.findByCardId(transaction.getCardId().get())
                .orElseThrow(() -> new ApplicationBusinessException("Card not found","NOT_FOUND"));
        return new TransactionOutputModel(
                transaction.getTransactionId(),
                transaction.getUserId(),
                category != null ? CategoryApplicationMapper.toOutput(category) : null,
                transaction.getPlanId().orElse(null),
                card != null ?CardApplicationMapper.toOutput(card) : null,
                transaction.getAmount(),
                transaction.getType(),
                transaction.getTitle(),
                transaction.getDescription(),
                transaction.getPaymentMethod(),
                transaction.getTransactionDate(),
                transaction.isFromRecurring(),
                transaction.getRecurringTransactionId().orElse(null),
                transaction.getTransferFromUserId().orElse(null),
                transaction.getTransferToUserId().orElse(null),
                transaction.getReceiverCardId().orElse(null),
                transaction.getTransactionStatus(),
                transaction.getCreatedAt()
        );
    }
}
