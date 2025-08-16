package com.application.pennypal.application.service.income;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.income.IncomeApplicationMapper;
import com.application.pennypal.application.dto.output.income.RecurringIncomeOutput;
import com.application.pennypal.application.dto.output.income.RecurringIncomesDataOutput;
import com.application.pennypal.application.port.in.Income.*;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.application.dto.input.income.IncomeInputModel;
import com.application.pennypal.application.dto.output.income.IncomeOutputModel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//@RequiredArgsConstructor
//public class IncomeService implements AddIncome, GetAllIncomes, GetRecentIncomes,
//        GetRecurringIncomesData, ToggleRecurrenceIncome, DeleteRecurringIncome {
////    private final IncomeRepositoryPort incomeRepositoryPort;
//    private final IncomeApplicationMapper incomeApplicationMapper;
//    private final TransactionRepositoryPort transactionRepositoryPort;
//    private final GenerateScheduledRecurringIncome generateScheduledRecurringIncome;
//
//    @Override
//    public IncomeOutputModel add(IncomeInputModel inputModel, Long userId){
//        if(!incomeRepositoryPort.existsDuplicateIncome(userId,inputModel.amount(),inputModel.title(),inputModel.incomeDate())){
//            Income income = new Income(
//                    null,
//                    userId,
//                    inputModel.title(),
//                    inputModel.amount(),
//                    inputModel.categoryId(),
//                    inputModel.incomeDate(),
//                    RecurringStatus.COMPLETED,
//                    inputModel.description(),
//                    inputModel.isRecurring(),
//                    inputModel.startDate(),
//                    inputModel.endDate(),
//                    inputModel.frequency(),
//                    inputModel.recurrenceActive(),
//                    null,
//                    null,
//                    false
//            );
//            Income newIncome = incomeRepositoryPort.save(income);
//
//            //if this income is for today, add a transaction
//            if(!newIncome.getIsRecurring() && (newIncome.getIncomeDate().isEqual(LocalDate.now()) || newIncome.getIncomeDate().isBefore(LocalDate.now()))){
//                if(!transactionRepositoryPort.exitsDuplicateTransaction(
//                        userId,
//                        newIncome.getAmount(),
//                        newIncome.getIncomeDate(),
//                        TransactionType.INCOME,
//                        newIncome.getId(),
//                        newIncome.getCategoryId()
//                )){
//                    String referenceId = newIncome.getIsRecurring()
//                            ? "recurring-income-" + income.getId()
//                            : "manual-income-" + income.getId();
//                    Transaction transaction =Transaction(
//                            userId,
//                            newIncome.getAmount(),
//                            newIncome.getIncomeDate(),
//                            TransactionType.INCOME,
//                            newIncome.getId(),
//                            TransactionStatus.COMPLETED,
//                            newIncome.getCategoryId(),
//                            "Transaction has been completed",
//                            "Instant Income Add",
//                            newIncome.getIsRecurring(),
//                            newIncome.getIsRecurring() ? newIncome.getId() : null,
//                            referenceId,
//                            null
//                    );
//                    transactionRepositoryPort.save(transaction);
//                }else{
//                    throw new ApplicationBusinessException("Duplicate transaction already exists","DUPLICATE_TRANSACTION");
//                }
//
//            }
//
//            /// Generate scheduled recurring logs
//            if(income.getIsRecurring()){
//                generateScheduledRecurringIncome.generate();
//            }
//
//            return incomeApplicationMapper.toOutput(newIncome);
//        }
//        else
//            throw new ApplicationBusinessException("Duplicate income already exists","DUPLICATE_INCOME");
//        return null;
//    }
//
//
//    @Override
//    public List<Income> get(Long userId,int size) {
////        return incomeRepositoryPort.getPagedIncomes(userId,size,0);
//        return null;
//    }
//
//    @Override
//    public List<Income> get(Long userId) {
////        return incomeRepositoryPort.getPagedIncomes(userId,10,0);
//        return null;
//    }
//
//    @Override
//    public RecurringIncomesDataOutput execute(Long userId) {
////        List<RecurringIncomeOutput> recurringIncomeDTOS =  incomeRepositoryPort.getRecurringIncomesData(userId);
////        BigDecimal totalMonthIncome =  recurringIncomeDTOS.stream()
////                .filter(RecurringIncomeOutput::active)
////                .map(recurringIncomeOutput -> {
////                    int occurrences = recurringIncomeOutput.frequency().getOccurrencesThisMonth(recurringIncomeOutput.startDate());
////                    return recurringIncomeOutput.amount().multiply(BigDecimal.valueOf(occurrences));
////                }).reduce(BigDecimal.ZERO,BigDecimal::add);
////        return new RecurringIncomesDataOutput(recurringIncomeDTOS,totalMonthIncome,recurringIncomeDTOS.toArray().length);
//        return null;
//    }
//
//    public void toggle(Long incomeId) {
////       Income income = incomeRepositoryPort.getIncomeById(incomeId).orElseThrow(()-> new ApplicationBusinessException("Income not found","NOT_FOUND"));
////       income.toggle();
////       incomeRepositoryPort.update(income);
//    }
//
//    @Override
//    public void deleteById(Long incomeId) {
////        this.incomeRepositoryPort.deleteById(incomeId);
//    }
//}
