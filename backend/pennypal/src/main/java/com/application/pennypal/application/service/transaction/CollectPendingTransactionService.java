package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.transaction.CollectPendingTransaction;
import com.application.pennypal.application.port.out.repository.RecurringLogRepositoryPort;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.domain.transaction.entity.RecurringTransactionLog;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.RecurringLogStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CollectPendingTransactionService implements CollectPendingTransaction {
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final RecurringTransactionRepositoryPort recurringTransactionRepositoryPort;
    private final RecurringLogRepositoryPort recurringLogRepositoryPort;

    @Override
    public void collect(String userId, String recurringLogId) {

        RecurringTransactionLog recurringIncomeLog = recurringLogRepositoryPort.getRecurringTransactionLogByIdAndUser(userId,recurringLogId)
                .orElseThrow(() -> new ApplicationBusinessException("Recurring income log cannot be found","NOT_FOUND"));

        /// Check the user is authenticated
        if(recurringIncomeLog.getUserId().equals(userId)){
            /// Complete transaction for pending recurring income

                /// update status of recurring log entity
                recurringIncomeLog = recurringIncomeLog.updateStatus(RecurringLogStatus.RECEIVED);
                recurringLogRepositoryPort.save(recurringIncomeLog);

                /// Get Recurring TransactionEntity
                RecurringTransaction recurringTransaction = recurringTransactionRepositoryPort.getTransactionByIdAndUser(userId,recurringIncomeLog.getRecurringId())
                        .orElseThrow(() -> new ApplicationBusinessException("Recurring income transaction entity cannot be found","NOT_FOUND"));


                /// Create a new Transaction
                Transaction newTransaction = getNewTransaction(recurringTransaction,recurringIncomeLog.getDateFor());
                transactionRepositoryPort.save(newTransaction);
//            else{
//            /// Managing non recurring incomes
//                income.setStatus(RecurringStatus.COMPLETED);
//                incomeRepositoryPort.update(income);
//                /// Get new newTransaction
//                Transaction newTransaction = getNewTransaction(income,income.getIncomeDate());
//                transactionRepositoryPort.save(newTransaction);
//            }
        }else{
            throw new ApplicationBusinessException("User action is not authenticated","UNAUTHORIZED_ACTION");
        }
    }

    private Transaction getNewTransaction(RecurringTransaction recurringTransaction, LocalDate paymentDate){
        return Transaction.create(
                recurringTransaction.getUserId(),
                recurringTransaction.getCategoryId(),
                recurringTransaction.getCardId().isPresent() ? recurringTransaction.getCardId().get():null,
                recurringTransaction.getAmount(),
                recurringTransaction.getTransactionType(),
                recurringTransaction.getTitle(),
                recurringTransaction.getDescription(),
                null,
                paymentDate,
                true,
                recurringTransaction.getRecurringId(),
                null,
                null

        );
    }
}
