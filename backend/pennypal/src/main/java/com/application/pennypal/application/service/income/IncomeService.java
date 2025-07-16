package com.application.pennypal.application.service.income;

import com.application.pennypal.application.exception.BusinessException;
import com.application.pennypal.application.mappers.income.IncomeApplicationMapper;
import com.application.pennypal.application.output.income.RecurringIncomeOutput;
import com.application.pennypal.application.output.income.RecurringIncomesDataOutput;
import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.usecases.Income.*;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.application.input.income.IncomeInputModel;
import com.application.pennypal.application.output.income.IncomeOutputModel;
import com.application.pennypal.domain.entity.Transactions;
import com.application.pennypal.domain.valueObject.IncomeStatus;
import com.application.pennypal.domain.valueObject.TransactionOriginType;
import com.application.pennypal.domain.valueObject.TransactionStatus;
import com.application.pennypal.shared.exception.ApplicationException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class IncomeService implements AddIncome, GetAllIncomes,GetRecentIncomes,
        GetRecurringIncomesData,ToggleRecurrenceIncome,DeleteRecurringIncome {
    private final IncomeRepositoryPort incomeRepositoryPort;
    private final IncomeApplicationMapper incomeApplicationMapper;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final GenerateScheduledRecurringIncome generateScheduledRecurringIncome;

    @Override
    public IncomeOutputModel add(IncomeInputModel inputModel, Long userId){
        if(!incomeRepositoryPort.existsDuplicateIncome(userId,inputModel.amount(),inputModel.title(),inputModel.incomeDate())){
            Income income = new Income(
                    null,
                    userId,
                    inputModel.title(),
                    inputModel.amount(),
                    inputModel.categoryId(),
                    inputModel.incomeDate(),
                    IncomeStatus.COMPLETED,
                    inputModel.description(),
                    inputModel.isRecurring(),
                    inputModel.startDate(),
                    inputModel.endDate(),
                    inputModel.frequency(),
                    inputModel.recurrenceActive(),
                    null,
                    null,
                    false
            );
            Income newIncome = incomeRepositoryPort.save(income);

            //if this income is for today, add a transaction
            if(!newIncome.getIsRecurring() && (newIncome.getIncomeDate().isEqual(LocalDate.now()) || newIncome.getIncomeDate().isBefore(LocalDate.now()))){
                if(!transactionRepositoryPort.exitsDuplicateTransaction(
                        userId,
                        newIncome.getAmount(),
                        newIncome.getIncomeDate(),
                        TransactionOriginType.INCOME,
                        newIncome.getId(),
                        newIncome.getCategoryId()
                )){
                    String referenceId = newIncome.getIsRecurring()
                            ? "recurring-income-" + income.getId()
                            : "manual-income-" + income.getId();
                    Transactions transactions = new Transactions(
                            userId,
                            newIncome.getAmount(),
                            newIncome.getIncomeDate(),
                            TransactionOriginType.INCOME,
                            newIncome.getId(),
                            TransactionStatus.COMPLETED,
                            newIncome.getCategoryId(),
                            "Transaction has been completed",
                            "Instant Income Add",
                            newIncome.getIsRecurring(),
                            newIncome.getIsRecurring() ? newIncome.getId() : null,
                            referenceId,
                            null
                    );
                    transactionRepositoryPort.save(transactions);
                }else{
                    throw new BusinessException("Duplicate transaction already exists","DUPLICATE_TRANSACTION");
                }

            }

            /// Generate scheduled recurring logs
            if(income.getIsRecurring()){
                generateScheduledRecurringIncome.generate();
            }

            return incomeApplicationMapper.toOutput(newIncome);
        }
        else
            throw new BusinessException("Duplicate income already exists","DUPLICATE_INCOME");
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
    public RecurringIncomesDataOutput execute(Long userId) {
        List<RecurringIncomeOutput> recurringIncomeDTOS =  incomeRepositoryPort.getRecurringIncomesData(userId);
        BigDecimal totalMonthIncome =  recurringIncomeDTOS.stream()
                .filter(RecurringIncomeOutput::active)
                .map(recurringIncomeOutput -> {
                    int occurrences = recurringIncomeOutput.frequency().getOccurrencesThisMonth(recurringIncomeOutput.startDate());
                    return recurringIncomeOutput.amount().multiply(BigDecimal.valueOf(occurrences));
                }).reduce(BigDecimal.ZERO,BigDecimal::add);
        return new RecurringIncomesDataOutput(recurringIncomeDTOS,totalMonthIncome,recurringIncomeDTOS.toArray().length);
    }

    public void toggle(Long incomeId) {
       Income income = incomeRepositoryPort.getIncomeById(incomeId).orElseThrow(()-> new ApplicationException("Income not found","NOT_FOUND"));
       income.toggle();
       incomeRepositoryPort.update(income);
    }

    @Override
    public void deleteById(Long incomeId) {
        this.incomeRepositoryPort.deleteById(incomeId);
    }
}
