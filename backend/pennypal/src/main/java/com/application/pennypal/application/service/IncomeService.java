package com.application.pennypal.application.service;

import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.usecases.Income.AddIncome;
import com.application.pennypal.application.usecases.Income.GetAllIncomes;
import com.application.pennypal.application.usecases.Income.GetTotalIncome;
import com.application.pennypal.domain.user.valueObject.IncomeDTO;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class IncomeService implements AddIncome, GetTotalIncome, GetAllIncomes {
    private final IncomeRepositoryPort incomeRepositoryPort;

    @Override
    public IncomeDTO add(IncomeDTO incomeDTO,Long userId){
        return incomeRepositoryPort.save(incomeDTO,userId);
    }

    @Override
    public BigDecimal get(Long userId, LocalDate date){
        return incomeRepositoryPort.getTotalIncomeByUserIdAndDate(userId,date);
    }

    @Override
    public List<IncomeDTO> get(Long userId) {
        return incomeRepositoryPort.getAllIncomes(userId);
    }
}
