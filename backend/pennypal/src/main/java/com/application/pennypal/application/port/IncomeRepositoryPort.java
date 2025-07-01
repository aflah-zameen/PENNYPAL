package com.application.pennypal.application.port;

import com.application.pennypal.domain.user.valueObject.IncomeDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepositoryPort {
    IncomeDTO save(IncomeDTO income,Long user_id);
    BigDecimal getTotalIncomeByUserIdAndDate(Long userId, LocalDate date);
    List<IncomeDTO> getAllIncomes(Long userId);
}
