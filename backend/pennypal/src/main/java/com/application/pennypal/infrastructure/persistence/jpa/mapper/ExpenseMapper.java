package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.application.dto.output.expense.ExpenseSummaryOutput;
import com.application.pennypal.domain.entity.Expense;
import com.application.pennypal.domain.valueObject.RecurringStatus;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.ExpenseEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.interfaces.rest.dtos.Expense.ExpenseSummaryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseSummaryResponseDTO toDTO(ExpenseSummaryOutput expenseSummaryOutput);

//
//    @Mappings({
//            @Mapping(target = "userId", source = "user.id"),
//            @Mapping(target = "categoryId", source = "category.id"),
//    })
//    Expense toDomain(ExpenseEntity entity);
//
//    @Mappings({
//            @Mapping(target = "id",ignore = true),
//            @Mapping(target = "description", source = "expense.description"),
//            @Mapping(target = "createdAt", ignore = true),
//            @Mapping(target = "updatedAt", ignore = true),
//            @Mapping(target = "user", source = "user"),
//            @Mapping(target = "category", source = "category"),
//            @Mapping(target = "status", expression = "java(mapStatus(expense.getExpenseDate()))")
//    })
//    ExpenseEntity toEntity(Expense expense, UserEntity user, CategoryEntity category);
//
//    default RecurringStatus mapStatus(LocalDate expenseDate) {
//        if(expenseDate != null)
//            return expenseDate.isAfter(LocalDate.now()) ? RecurringStatus.PENDING : RecurringStatus.COMPLETED;
//        else
//            return RecurringStatus.RECURRING;
//    }
}
