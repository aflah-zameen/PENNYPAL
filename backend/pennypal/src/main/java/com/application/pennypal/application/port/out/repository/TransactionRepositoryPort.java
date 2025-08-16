package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.card.CardExpenseOverviewOutputModel;
import com.application.pennypal.application.dto.output.card.CardSpendingOutputModel;
import com.application.pennypal.application.dto.output.transaction.*;
import com.application.pennypal.application.dto.output.wallet.WalletStatsOutputModel;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepositoryPort {
    BigDecimal getTotalAmountForMonthByTransactionType(String userId,LocalDate start, LocalDate end,TransactionType transactionType);
    Transaction save(Transaction transaction);
    boolean exitsDuplicateTransaction(String userId, String recurringTransactionDate, LocalDate transactionDate,
                                      TransactionType transactionType);

    List<Transaction> findRecentTransaction(String userId, int size,TransactionType transactionType);

    Optional<Transaction> getTransactionByIdAndTransactionType(String incomeId, TransactionType transactionType);

    BigDecimal getTotalAmountByCardAndType(String cardId, TransactionType transactionType);


    CardSpendingOutputModel findMonthlySpending(String cardId);

    CardSpendingOutputModel findWeeklySpending(String cardId);

    CardSpendingOutputModel findYearlySpending(String cardId);

    List<CardExpenseOverviewOutputModel> calculateCardExpenseOverview(String cardId,LocalDate startDate, LocalDate endDate);

    List<Transaction> getCardTransaction(String cardId, int page, int size);

    List<Transaction> getAllSpendTransaction(String userId);

    CategorySpendingOutputModel getTopCategorySpending(String userId, TransactionType transactionType);

    MostUsedCard getMostUsedCard(String userId, TransactionType transactionType);

    List<Transaction> findUserInvolvedTransactions(String userId);

    List<DashIncExpChart> getIncomeExpenseBetween(String userId,LocalDate startDate, LocalDate endDate);

    List<ExpenseDataChart> getExpenseBreakdownData(String userId, LocalDate startDate,LocalDate endDate);

    WalletStatsOutputModel getWalletStats(String userId);

    List<Transaction> getWalletTransactions(String userId);
}
