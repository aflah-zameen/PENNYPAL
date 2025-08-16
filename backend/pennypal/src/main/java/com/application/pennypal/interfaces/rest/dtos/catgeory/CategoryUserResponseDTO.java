package com.application.pennypal.interfaces.rest.dtos.catgeory;

import com.application.pennypal.domain.valueObject.CategoryType;

import java.util.List;

public record CategoryUserResponseDTO(String id, String name, List<String> usageTypes, boolean active,
                                  boolean isDefault, int sortOrder, String color, String icon)  {
}

