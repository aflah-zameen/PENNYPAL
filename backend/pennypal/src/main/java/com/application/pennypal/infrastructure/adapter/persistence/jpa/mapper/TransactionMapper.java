package com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper;

import com.application.pennypal.domain.entity.Category;
import com.application.pennypal.domain.entity.Transactions;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.TransactionEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "categoryId", source = "category.id")
    Transactions toDomain(TransactionEntity transactionEntity);

    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "description", source = "transactions.description"),
            @Mapping(target = "createdAt",ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "category", source = "category")
    })
    TransactionEntity toEntity(Transactions transactions, UserEntity user, CategoryEntity category);
}
