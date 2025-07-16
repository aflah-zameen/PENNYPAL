package com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper;

import com.application.pennypal.domain.entity.Category;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(source = "usageTypes", target = "usageTypes")
    })
    CategoryEntity toEntity(Category category);
    @Mappings({
            @Mapping(target = "isActive", source = "active"),
            @Mapping(source = "usageTypes", target = "usageTypes"),
            @Mapping(target = "isDefault",source = "default")
    })
    Category toDomain(CategoryEntity entity);
}
