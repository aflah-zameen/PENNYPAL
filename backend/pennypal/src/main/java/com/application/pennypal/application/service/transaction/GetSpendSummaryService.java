package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.output.transaction.CategorySpendingOutputModel;
import com.application.pennypal.application.dto.output.transaction.MonthlyComparison;
import com.application.pennypal.application.dto.output.transaction.MostUsedCard;
import com.application.pennypal.application.dto.output.transaction.SpendSummaryOutputModel;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.port.in.transaction.GetSpendSummary;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@RequiredArgsConstructor
public class GetSpendSummaryService implements GetSpendSummary {

    private final TransactionRepositoryPort transactionRepositoryPort;

    @Override
    public SpendSummaryOutputModel execute(String userId) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        BigDecimal totalSpend = transactionRepositoryPort.getTotalAmountForMonthByTransactionType(userId,startDate,endDate, TransactionType.EXPENSE);
        CategorySpendingOutputModel topCategory = transactionRepositoryPort.getTopCategorySpending(userId,TransactionType.EXPENSE);
        MostUsedCard mostUsedCard = transactionRepositoryPort.getMostUsedCard(userId,TransactionType.EXPENSE);

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfLastMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfLastMonth = today.withDayOfMonth(1).minusDays(1);
        BigDecimal totalPreviousSpend = transactionRepositoryPort.getTotalAmountForMonthByTransactionType(userId,firstDayOfLastMonth,lastDayOfLastMonth, TransactionType.EXPENSE);

        /// Calculate trend comparing to last month

        MonthlyComparison monthlyComparison = getMonthlyComparison(totalPreviousSpend, totalSpend);

        return new SpendSummaryOutputModel(
                totalSpend,
                topCategory,
                mostUsedCard,
                monthlyComparison
        );
    }

    private static MonthlyComparison getMonthlyComparison(BigDecimal totalPreviousSpend, BigDecimal totalSpend) {
        BigDecimal changePercentage = BigDecimal.ZERO;
        String trend = null;
        if (totalPreviousSpend.compareTo(BigDecimal.ZERO) != 0) {
            changePercentage = totalSpend
                    .subtract(totalPreviousSpend)
                    .divide(totalPreviousSpend, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));

            trend = changePercentage.compareTo(BigDecimal.ZERO) > 0 ? "increase" :"decrease";
        }
        return new MonthlyComparison(changePercentage.doubleValue(),trend, totalPreviousSpend);

    }
}
