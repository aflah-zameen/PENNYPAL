package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.income.PendingIncomeSummaryOutput;
import com.application.pennypal.application.dto.output.income.RecurringIncomeOutput;
import com.application.pennypal.domain.entity.Income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//public interface IncomeRepositoryPort {
//    Income save(Income income);
//    Income update(Income income);
//    List<Income> getPagedIncomes(Long userId,int size,int page);
//    List<RecurringIncomeOutput>     getRecurringIncomesData(Long userId);
//    Optional<Income> getIncomeById(Long incomeId);
//    void deleteById(Long incomeId);
//    List<Income> findAllActiveRecurringIncomes();
//    PendingIncomeSummaryOutput getTotalPendingOneTimeIncome(Long userId, LocalDate currentMonthStartDate, LocalDate currentMonthEndDate);
//    Integer countActiveRecurringIncomeByUserId(Long userId);
//    boolean existsDuplicateIncome(Long userId, BigDecimal amount,String title,LocalDate incomeDate);
//    List<Income> findAllOneTimePendingIncomes(Long userId,LocalDate date );
//}
