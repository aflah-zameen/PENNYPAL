package com.application.pennypal.application.mappers.income;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.application.dto.output.income.IncomeOutputModel;
import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.entity.Income;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class    IncomeApplicationMapper {
    protected final CategoryManagementRepositoryPort categoryManagementRepositoryPort;

    public IncomeOutputModel toOutput(Income income){
        Category category = categoryManagementRepositoryPort.findById(income.getCategoryId())
                .orElseThrow(() -> new ApplicationBusinessException("Category not found","NOT_FOUND"));
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
