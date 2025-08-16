package com.application.pennypal.interfaces.rest.mappers;

import com.application.pennypal.application.dto.input.expense.ExpenseInputModel;
import com.application.pennypal.application.dto.output.expense.AllPendingExpenseSummaryOutput;
import com.application.pennypal.application.dto.output.expense.ExpenseOutputModel;
import com.application.pennypal.application.dto.output.expense.ExpenseSummaryOutput;
import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.interfaces.rest.dtos.Expense.*;

import java.time.LocalDate;

public class ExpenseDtoMapper {
    public static ExpenseInputModel toInput(AddExpenseRequest addExpenseRequestDTO){
        return new ExpenseInputModel(
                addExpenseRequestDTO.title().trim(),
                addExpenseRequestDTO.amount(),
                addExpenseRequestDTO.categoryId(),
                addExpenseRequestDTO.expenseDate() != null ? LocalDate.parse(addExpenseRequestDTO.expenseDate().trim()) : null,
                addExpenseRequestDTO.description().trim(),
                addExpenseRequestDTO.isRecurring(),
                addExpenseRequestDTO.startDate() != null && addExpenseRequestDTO.isRecurring() ? LocalDate.parse(addExpenseRequestDTO.startDate()) : null,
                addExpenseRequestDTO.endDate() != null && addExpenseRequestDTO.isRecurring() ? LocalDate.parse(addExpenseRequestDTO.endDate()) : null,
                addExpenseRequestDTO.frequency() != null && addExpenseRequestDTO.isRecurring() ? RecurrenceFrequency.valueOf(addExpenseRequestDTO.frequency().trim().toUpperCase()) : null,
                addExpenseRequestDTO.isRecurring()
        );
    }

    public static ExpenseResponseDTO toResponse(ExpenseOutputModel expenseOutputModel){
        return new ExpenseResponseDTO(
                expenseOutputModel.id(),
                expenseOutputModel.amount(),
                expenseOutputModel.title(),
                CategoryDtoMapper.toResponse(expenseOutputModel.category()),
                expenseOutputModel.expenseDate(),
                expenseOutputModel.status().getValue(),
                expenseOutputModel.description(),
                expenseOutputModel.isRecurring(),
                expenseOutputModel.frequency() != null ? expenseOutputModel.frequency().getValue() : null,
                expenseOutputModel.startDate(),
                expenseOutputModel.endDate(),
                expenseOutputModel.updatedAt(),
                expenseOutputModel.createdAt(),
                expenseOutputModel.recurrenceActive(),
                expenseOutputModel.deleted()
        );
    }

    public static ExpenseSummaryResponseDTO toResponse(ExpenseSummaryOutput expenseSummaryOutput){
        return new ExpenseSummaryResponseDTO(
                new TotalExpenseSummaryDTO(
                        expenseSummaryOutput.totalExpenseSummaryOutput().totalExpenses(),
                        expenseSummaryOutput.totalExpenseSummaryOutput().progressValue()
                ),
                new PendingExpenseSummaryDTO(
                        expenseSummaryOutput.pendingExpenseSummaryOutput().totalAmount(),
                        expenseSummaryOutput.pendingExpenseSummaryOutput().pendingExpenses()
                ),
                new ActiveRecurringExpenseDTO(
                        expenseSummaryOutput.activeRecurringExpenseOutput().count()
                )
        );
    }

    public static AllPendingExpenseSummaryDTO toResponse(AllPendingExpenseSummaryOutput allPendingExpenseSummaryOutput)
    {
        return new AllPendingExpenseSummaryDTO(
                allPendingExpenseSummaryOutput.pendingExpenseOutputList().stream()
                        .map(pendingExpenseOutput ->
                                new PendingExpenseResponseDTO(
                                        pendingExpenseOutput.expenseId(),
                                        pendingExpenseOutput.title(),
                                        pendingExpenseOutput.amount(),
                                        pendingExpenseOutput.paymentDate(),
                                        CategoryDtoMapper.toResponse(pendingExpenseOutput.category())
                                )).toList(),
                allPendingExpenseSummaryOutput.totalCount(),
                allPendingExpenseSummaryOutput.totalAmount()
        );
    }
}
