package com.application.pennypal.interfaces.rest.mappers;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;


public class CategoryDtoMapper {
    public static CategoryUserResponseDTO toResponse(CategoryUserOutput categoryUserOutput){
        return new CategoryUserResponseDTO(
                categoryUserOutput.categoryId(),
                categoryUserOutput.name(),
                categoryUserOutput.usageTypes().stream().map(Object::toString).toList(),
                categoryUserOutput.active(),
                categoryUserOutput.isDefault(),
                categoryUserOutput.sortOrder(),
                categoryUserOutput.color(),
                categoryUserOutput.icon()
        );
    }
}
