package com.application.pennypal.application.service.income;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.output.income.AllPendingIncomeSummaryOutput;
import com.application.pennypal.application.output.income.PendingIncomeOutput;
import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.port.RecurringIncomeLogRepositoryPort;
import com.application.pennypal.application.usecases.Income.GetAllPendingIncomeSummary;
import com.application.pennypal.domain.entity.Category;
import com.application.pennypal.domain.entity.Income;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class GetAllPendingIncomeSummaryService implements GetAllPendingIncomeSummary {
    private final IncomeRepositoryPort incomeRepositoryPort;
    private final RecurringIncomeLogRepositoryPort recurringIncomeLogRepositoryPort;
    private final CategoryManagementRepositoryPort categoryManagementRepositoryPort;
    @Override
    public AllPendingIncomeSummaryOutput execute(Long userId) {

        /// Adding All pending incomes entities
        List<PendingIncomeOutput> pendingIncomeOutputList = new ArrayList<>();

            /// Adding One time pending income entities
            List<Income> oneTimePendingIncomes = incomeRepositoryPort.findAllOneTimePendingIncomes(userId, LocalDate.now());
            for(Income income : oneTimePendingIncomes){
                Category category = categoryManagementRepositoryPort.findById(income.getCategoryId())
                                .orElseThrow(() -> new ApplicationBusinessException("Category entity not found","NOT_FOUND"));
                pendingIncomeOutputList.add(new PendingIncomeOutput(
                        income.getId(),
                        income.getTitle(),
                        income.getAmount(),
                        income.getIncomeDate(),
                        CategoryApplicationMapper.toOutput(category)
                ));
            }

            /// Adding recurring pending income entities
            List<Income> recurringPendingIncomes = recurringIncomeLogRepositoryPort.findAllPendingRecurringIncomeLogs(userId,LocalDate.now());
            for(Income income : recurringPendingIncomes) {
                Category category = categoryManagementRepositoryPort.findById(income.getCategoryId())
                        .orElseThrow(() -> new ApplicationBusinessException("Category entity not found", "NOT_FOUND"));
                pendingIncomeOutputList.add(new PendingIncomeOutput(
                        income.getId(),
                        income.getTitle(),
                        income.getAmount(),
                        income.getIncomeDate(),
                        CategoryApplicationMapper.toOutput(category)
                ));
            }



        /// Calculate total pending count
        int totalCount = pendingIncomeOutputList.size();

        /// Total pending amount
        BigDecimal totalAmount = pendingIncomeOutputList.stream()
                .map(PendingIncomeOutput::amount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        /// Sorting recurring pending based on incomeDate
        List<PendingIncomeOutput> sortedList = pendingIncomeOutputList.stream()
                .sorted(Comparator.comparing(
                        PendingIncomeOutput::incomeDate,
                        Comparator.nullsFirst(Comparator.naturalOrder())
                ).reversed())
                .toList();

        return new AllPendingIncomeSummaryOutput(sortedList,totalCount,totalAmount);
    }
}
