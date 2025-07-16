package com.application.pennypal.application.mappers.expense;

import com.application.pennypal.application.exception.BusinessException;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.output.category.CategoryUserOutput;
import com.application.pennypal.application.output.expense.ExpenseOutputModel;
import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import com.application.pennypal.domain.entity.Category;
import com.application.pennypal.domain.entity.Expense;
import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExpenseApplicationMapper {
    private final CategoryManagementRepositoryPort categoryManagementRepositoryPort;
    public ExpenseOutputModel toOutput(Expense expense){
        Category category = categoryManagementRepositoryPort.findById(expense.getCategoryId())
                .orElseThrow(() -> new BusinessException("Category not found","NOT_FOUND"));
        return new ExpenseOutputModel(
                expense.getId(),
                expense.getName(),
                CategoryApplicationMapper.toOutput(category),
                expense.getAmount(),
                expense.getStartDate(),
                expense.getEndDate(),
                RecurrenceFrequency.valueOf(expense.getType()),
                expense.isDeleted()
        );
    }
}
