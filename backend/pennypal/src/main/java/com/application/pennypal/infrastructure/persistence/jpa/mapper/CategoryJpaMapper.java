package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;

public class CategoryJpaMapper {
    public static Category toDomain(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }

        return Category.reconstruct(
                categoryEntity.getCategoryId(),
                categoryEntity.getCreatedBy(),
                categoryEntity.getName(),
                categoryEntity.getUsageTypes() == null
                        ? new ArrayList<>()
                        : new ArrayList<>(categoryEntity.getUsageTypes())
                ,
                categoryEntity.getCreatedAt(),
                categoryEntity.getUpdatedAt(),
                categoryEntity.getSortOrder(),
                categoryEntity.getDescription(),
                categoryEntity.getColor(),
                categoryEntity.isActive(),
                categoryEntity.isDefault(),
                categoryEntity.getIcon(),
                categoryEntity.getUsageCount()
        );
    }

    public static CategoryEntity toEntity(Category category) {
        if (category == null) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(category.getCategoryId());
        categoryEntity.setCreatedBy(category.getCreatedBy());
        categoryEntity.setName(category.getName());
        categoryEntity.setUsageTypes(
                category.getUsageTypes() == null ? new ArrayList<>() : new ArrayList<>(category.getUsageTypes())
        );

        categoryEntity.setCreatedAt(category.getCreatedAt());
        categoryEntity.setUpdatedAt(category.getUpdatedAt());
        categoryEntity.setSortOrder(category.getSortOrder());
        categoryEntity.setDescription(category.getDescription());
        categoryEntity.setColor(category.getColor());
        categoryEntity.setActive(category.isActive());
        categoryEntity.setDefault(category.isDefault());
        categoryEntity.setIcon(category.getIcon());
        categoryEntity.setUsageCount(category.getUsageCount());
        return categoryEntity;
    }

}
