package com.application.pennypal.application.port;

import com.application.pennypal.domain.entity.Transaction;
import com.application.pennypal.domain.valueObject.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepositoryPort {
    BigDecimal getTotalAmountForMonth(LocalDate start, LocalDate end);
    Transaction save(Transaction transaction);
    boolean exitsDuplicateTransaction(Long userId, BigDecimal amount, LocalDate transactionDate,
                                      TransactionType type, Long originId, Long categoryId);

    List<Transaction> findRecentIncomeTransaction(Long userId, int size);

    List<Transaction> findAllByUserIdAndOriginIdAndType(Long userId, Long id, TransactionType type);
}
