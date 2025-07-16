package com.application.pennypal.interfaces.rest.mappers;

import com.application.pennypal.application.output.expense.ExpenseOutputModel;
import com.application.pennypal.interfaces.rest.dtos.Expense.ExpenseResponseDTO;

public class ExpenseDtoMapper {
    public static ExpenseResponseDTO toDTO(ExpenseOutputModel expenseOutputModel){
        return new ExpenseResponseDTO(
                expenseOutputModel.id(),
                expenseOutputModel.name(),
                CategoryDtoMapper.toResponse(expenseOutputModel.category()),
                expenseOutputModel.amount(),
                expenseOutputModel.startDate(),
                expenseOutputModel.endDate(),
                expenseOutputModel.type().getValue()
        );
    }
}
