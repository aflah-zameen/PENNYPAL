package com.application.pennypal.interfaces.rest.dtos.catgeory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CategoryRequestDTO(@NotBlank(message = "Name cannot be null") String name,
                                 @NotBlank(message = "Color cannot be null") String color,
                                 @NotNull(message = "category usage type cannot be null") List<String> usageTypes,
                                 @NotNull(message = "Active value cannot be null.")boolean active,
                                 @NotNull(message = "Default value cannot be null.") boolean isDefault,
                                 @NotNull(message = "Sort order value cannot be null.") int sortOrder,
                                 @NotNull(message = "Usage count value cannot be null.")  int usageCount,
                                 @NotNull(message = "Icon cant be null") String icon,
                                 String description,
                                 String categoryId) { }
