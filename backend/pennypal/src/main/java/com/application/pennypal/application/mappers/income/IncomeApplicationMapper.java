package com.application.pennypal.application.mappers.income;

import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.output.category.CategoryUserOutput;
import com.application.pennypal.application.output.income.IncomeOutputModel;
import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import com.application.pennypal.domain.entity.Category;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.shared.exception.ApplicationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class    IncomeApplicationMapper {
    protected final CategoryManagementRepositoryPort categoryManagementRepositoryPort;

    public IncomeOutputModel toOutput(Income income){
        Category category = categoryManagementRepositoryPort.findById(income.getCategoryId())
                .orElseThrow(() -> new ApplicationException("Category not found","NOT_FOUND"));
        CategoryUserOutput categoryUserOutput = CategoryApplicationMapper.toOutput(category);
        return new IncomeOutputModel(
                income.getId(),
                income.getTitle(),
                income.getAmount(),
                categoryUserOutput,
                income.getIncomeDate(),
                income.getStatus(),
                income.getDescription(),
                income.getIsRecurring(),
                income.getFrequency(),
                income.getStartDate(),
                income.getEndDate(),
                income.getUpdatedAt(),
                income.getCreatedAt(),
                income.isRecurrenceActive(),
                income.isDeleted()
        );
    }
}
