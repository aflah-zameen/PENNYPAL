package com.application.pennypal.interfaces.rest.dtos.catgeory;

import com.application.pennypal.domain.valueObject.CategoryType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record CategoryAdminResponse(
        String categoryId,
        String createdBy,
         String name,
         List<CategoryType>usageTypes,
         LocalDateTime createdAt,
         LocalDateTime updatedAt,
         int sortOrder,
         String description,
         String color,
         boolean active,
         @JsonProperty("isDefault")
         boolean isDefault,
         String icon,
         int usageCount) {
}
