package com.application.pennypal.application.port;

import com.application.pennypal.application.dto.RecurringIncomeDTO;
import com.application.pennypal.application.dto.RecurringIncomesDataDTO;
import com.application.pennypal.domain.user.entity.Income;
import com.application.pennypal.domain.user.valueObject.IncomeDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepositoryPort {
    Income save(IncomeDTO income, Long user_id);
    Income update(Income income);
    BigDecimal getTotalIncomeByUserIdAndDate(Long userId, LocalDate date);
    List<Income> getPagedIncomes(Long userId,int size,int page);
    List<RecurringIncomeDTO> getRecurringIncomesData(Long userId);
    Optional<Income> getIncomeById(Long incomeId);
    void deleteById(Long incomeId);
}
