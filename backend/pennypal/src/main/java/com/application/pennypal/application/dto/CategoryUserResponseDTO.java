package com.application.pennypal.application.dto;

import com.application.pennypal.domain.user.valueObject.CategoryType;

import java.util.List;

public record CategoryUserResponseDTO(Long id, String name, List<CategoryType> usageTypes, boolean active,
                                      boolean isDefault, int sortOrder) {
}
