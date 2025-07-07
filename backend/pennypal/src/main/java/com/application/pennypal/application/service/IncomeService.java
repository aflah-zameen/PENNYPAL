package com.application.pennypal.application.service;

import com.application.pennypal.application.dto.RecurringIncomeDTO;
import com.application.pennypal.application.dto.RecurringIncomesDataDTO;
import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.usecases.Income.*;
import com.application.pennypal.domain.user.entity.Income;
import com.application.pennypal.domain.user.valueObject.IncomeDTO;
import com.application.pennypal.shared.exception.ApplicationException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class IncomeService implements AddIncome, GetTotalIncome, GetAllIncomes,GetRecentIncomes,
        GetRecurringIncomesData,ToggleRecurrenceIncome,DeleteRecurringIncome {
    private final IncomeRepositoryPort incomeRepositoryPort;

    @Override
    public Income add(IncomeDTO incomeDTO, Long userId){
        return incomeRepositoryPort.save(incomeDTO,userId);
    }

    @Override
    public BigDecimal get(Long userId, LocalDate date){
        return incomeRepositoryPort.getTotalIncomeByUserIdAndDate(userId,date);
    }

    @Override
    public List<Income> get(Long userId,int size) {
        return incomeRepositoryPort.getPagedIncomes(userId,size,0);
    }

    @Override
    public List<Income> get(Long userId) {
        return incomeRepositoryPort.getPagedIncomes(userId,10,0);
    }

    @Override
    public RecurringIncomesDataDTO execute(Long userId) {
        List<RecurringIncomeDTO> recurringIncomeDTOS =  incomeRepositoryPort.getRecurringIncomesData(userId);
        BigDecimal totalMonthIncome =  recurringIncomeDTOS.stream()
                .filter(RecurringIncomeDTO::active)
                .map(recurringIncomeDTO -> {
                    int occurrences = recurringIncomeDTO.frequency().getOccurrencesThisMonth(recurringIncomeDTO.incomeDate());
                    return recurringIncomeDTO.amount().multiply(BigDecimal.valueOf(occurrences));
                }).reduce(BigDecimal.ZERO,BigDecimal::add);
        return new RecurringIncomesDataDTO(recurringIncomeDTOS,totalMonthIncome,recurringIncomeDTOS.toArray().length);
    }

    public RecurringIncomeDTO toggle(Long incomeId) {
       Income income = incomeRepositoryPort.getIncomeById(incomeId).orElseThrow(()-> new ApplicationException("Income not found","NOT_FOUND"));
       income.toggle();
       income = incomeRepositoryPort.update(income);
       return new RecurringIncomeDTO(income.getId(),income.isRecurrenceActive(),income.getAmount(),income.getIncome_date(),
               income.getSource().getName(),income.getRecurrence(),income.getFrequency());
    }

    @Override
    public void deleteById(Long incomeId) {
        this.incomeRepositoryPort.deleteById(incomeId);
    }
}
