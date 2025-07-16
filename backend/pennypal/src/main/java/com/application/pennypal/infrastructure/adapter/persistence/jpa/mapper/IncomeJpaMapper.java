package com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper;

import com.application.pennypal.application.output.income.IncomeSummaryOutput;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.valueObject.IncomeStatus;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.IncomeEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import com.application.pennypal.interfaces.rest.dtos.income.IncomeSummaryResponseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface IncomeJpaMapper {
    IncomeSummaryResponseDTO toDTO(IncomeSummaryOutput incomeSummaryOutput);


    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "categoryId", source = "category.id"),
    })
    Income toDomain(IncomeEntity entity);

    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "description", source = "income.description"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "category", source = "category"),
            @Mapping(target = "status", expression = "java(mapStatus(income.getIncomeDate()))")
    })
    IncomeEntity toEntity(Income income, UserEntity user, CategoryEntity category);

    default IncomeStatus mapStatus(LocalDate incomeDate) {
        if(incomeDate != null)
            return incomeDate.isAfter(LocalDate.now()) ? IncomeStatus.PENDING : IncomeStatus.COMPLETED;
        else
            return IncomeStatus.RECURRING;
    }
}
