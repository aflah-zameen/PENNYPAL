package com.application.pennypal.application.mappers.transaction;

import com.application.pennypal.application.dto.output.transaction.RecurringTransactionOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.card.CardApplicationMapper;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import com.application.pennypal.domain.card.entity.Card;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.exception.category.CategoryRequiredExceptionDomain;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecurringTransactionApplicationMapper {
    private final CategoryManagementRepositoryPort categoryManagementRepositoryPort;
    private final CardRepositoryPort cardRepositoryPort;

    public RecurringTransactionOutputModel toOutput(RecurringTransaction transaction){
        Category category = categoryManagementRepositoryPort.findByCategoryId(transaction.getCategoryId())
                .orElseThrow(CategoryRequiredExceptionDomain::new);
        Card card = null;
        if(transaction.getCardId().isPresent())
            card = cardRepositoryPort.findByCardId(transaction.getCardId().get())
                    .orElseThrow(() -> new ApplicationBusinessException("Card not found","NOT_FOUND"));
        return new RecurringTransactionOutputModel(
                transaction.getRecurringId(),
                transaction.getUserId(),
                card != null ? CardApplicationMapper.toOutput(card) :null,
                CategoryApplicationMapper.toOutput(category),
                transaction.getTransactionType(),
                transaction.getTitle(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getFrequency(),
                transaction.getStartDate(),
                transaction.getEndDate(),
                transaction.getLastGeneratedDate(),
                transaction.getActive(),
                transaction.getCreatedAt()
        );
    }
}
