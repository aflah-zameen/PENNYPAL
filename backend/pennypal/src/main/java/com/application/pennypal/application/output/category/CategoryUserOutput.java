package com.application.pennypal.application.output.category;

import com.application.pennypal.domain.valueObject.CategoryType;

import java.util.List;

public record CategoryUserOutput(Long id, String name, List<CategoryType> usageTypes, boolean active,
                                 boolean isDefault, int sortOrder, String color, String icon) {
}
