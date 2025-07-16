package com.application.pennypal.application.port;

import com.application.pennypal.domain.entity.Transactions;
import com.application.pennypal.domain.valueObject.TransactionOriginType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepositoryPort {
    BigDecimal getTotalAmountForMonth(LocalDate start, LocalDate end);
    Transactions save(Transactions transactions);
    boolean exitsDuplicateTransaction(Long userId, BigDecimal amount, LocalDate transactionDate,
                                      TransactionOriginType type,Long originId,Long categoryId);

    List<Transactions> findRecentIncomeTransaction(Long userId, int size);

    List<Transactions> findAllByUserIdAndOriginIdAndType(Long userId, Long id,TransactionOriginType type);
}
