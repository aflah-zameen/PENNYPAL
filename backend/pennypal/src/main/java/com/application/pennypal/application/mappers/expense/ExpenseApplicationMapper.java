package com.application.pennypal.application.mappers.expense;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.application.dto.output.expense.ExpenseOutputModel;
import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.entity.Expense;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExpenseApplicationMapper {
    private final CategoryManagementRepositoryPort categoryManagementRepositoryPort;
    public ExpenseOutputModel toOutput(Expense expense){
        Category category = categoryManagementRepositoryPort.findById(expense.getCategoryId())
                .orElseThrow(() -> new ApplicationBusinessException("Category not found","NOT_FOUND"));
        CategoryUserOutput categoryUserOutput = CategoryApplicationMapper.toOutput(category);
        return new ExpenseOutputModel(
                expense.getId(),
                expense.getTitle(),
                expense.getAmount(),
                categoryUserOutput,
                expense.getExpenseDate(),
                expense.getStatus(),
                expense.getDescription(),
                expense.isRecurring(),
                expense.getFrequency(),
                expense.getStartDate(),
                expense.getEndDate(),
                expense.getUpdatedAt(),
                expense.getCreatedAt(),
                expense.isRecurrenceActive(),
                expense.isDeleted()
        );
    }
}
