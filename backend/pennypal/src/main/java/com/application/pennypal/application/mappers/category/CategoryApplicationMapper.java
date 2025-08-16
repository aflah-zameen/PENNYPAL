package com.application.pennypal.application.mappers.category;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.domain.catgeory.entity.Category;

public class CategoryApplicationMapper {
    public static CategoryUserOutput toOutput(Category category){
        return new CategoryUserOutput(
                category.getCategoryId(),
                category.getName(),
                category.getUsageTypes(),
                category.isActive(),
                category.isDefault(),
                category.getSortOrder(),
                category.getColor(),
                category.getIcon()
        );
    }
}
