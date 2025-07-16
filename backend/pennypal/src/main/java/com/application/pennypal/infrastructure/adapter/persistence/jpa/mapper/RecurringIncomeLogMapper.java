package com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper;

import com.application.pennypal.domain.entity.RecurringIncomeLog;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.RecurringIncomeLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RecurringIncomeLogMapper {
    @Mappings({
            @Mapping(target = "userId",source = "user.id"),
            @Mapping(target = "incomeId",source = "income.id")
    })
    RecurringIncomeLog toDomain (RecurringIncomeLogEntity recurringIncomeLogEntity);
}
