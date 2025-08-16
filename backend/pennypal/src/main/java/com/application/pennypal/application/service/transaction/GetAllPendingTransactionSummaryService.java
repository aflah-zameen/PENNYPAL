package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.output.transaction.AllPendingTransactionSummaryOutput;
import com.application.pennypal.application.dto.output.transaction.PendingTransactionOutput;
import com.application.pennypal.application.dto.output.transaction.RecurringTransactionOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.transaction.RecurringTransactionApplicationMapper;
import com.application.pennypal.application.port.out.repository.RecurringLogRepositoryPort;
import com.application.pennypal.application.port.in.transaction.GetAllPendingTransactionSummary;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.domain.transaction.entity.RecurringTransactionLog;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class GetAllPendingTransactionSummaryService implements GetAllPendingTransactionSummary {
    private final RecurringLogRepositoryPort recurringLogRepositoryPort;
    private final RecurringTransactionRepositoryPort recurringTransactionRepositoryPort;
    private final RecurringTransactionApplicationMapper recurringTransactionApplicationMapper;
    @Override
    public AllPendingTransactionSummaryOutput execute(String userId, TransactionType transactionType) {

        /// Adding All pending entities
        List<PendingTransactionOutput> pendingOutputList = new ArrayList<>();

            /// Adding One time pending income entities
//            List<Income> oneTimePendingIncomes = incomeRepositoryPort.findAllOneTimePendingIncomes(userId, LocalDate.now());
//            for(Income income : oneTimePendingIncomes){
//                Category category = categoryManagementRepositoryPort.findById(income.getCategoryId())
//                                .orElseThrow(() -> new ApplicationBusinessException("Category entity not found","NOT_FOUND"));
//                pendingIncomeOutputList.add(new PendingIncomeOutput(
//                        income.getId(),
//                        income.getTitle(),
//                        income.getAmount(),
//                        income.getIncomeDate(),
//                        CategoryApplicationMapper.toOutput(category)
//                ));
//            }

            /// Adding recurring pending income entities
            List<RecurringTransactionLog> recurringPendingTransactions = recurringLogRepositoryPort.findAllPendingRecurringLogs(userId,transactionType);
            for(RecurringTransactionLog log : recurringPendingTransactions) {
                RecurringTransaction recurringTransaction = recurringTransactionRepositoryPort.getTransactionByIdAndUser(userId,log.getRecurringId())
                        .orElseThrow(() -> new ApplicationBusinessException("Recurrence transaction entity not found","NOT_FOUND"));
                RecurringTransactionOutputModel outputModel = recurringTransactionApplicationMapper.toOutput(recurringTransaction);
                PendingTransactionOutput pendingTransactionOutput = new PendingTransactionOutput(
                        log.getTransactionId(),
                        outputModel.title(),
                        outputModel.amount(),
                        outputModel.category(),
                        outputModel.card(),
                        outputModel.transactionType(),
                        outputModel.description(),
                        log.getDateFor(),
                        log.getStatus()
                );
                pendingOutputList.add(pendingTransactionOutput);
            }




        /// Calculate total pending count
        int totalCount = pendingOutputList.size();

        /// Total pending amount
        BigDecimal totalAmount = pendingOutputList.stream()
                .map(PendingTransactionOutput::amount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        /// Sorting recurring pending based on incomeDate
        List<PendingTransactionOutput> sortedList = pendingOutputList.stream()
                .sorted(Comparator.comparing(
                        PendingTransactionOutput::transactionDate,
                        Comparator.nullsFirst(Comparator.naturalOrder())
                ).reversed())
                .toList();

        return new AllPendingTransactionSummaryOutput(sortedList,totalCount,totalAmount);
    }
}
