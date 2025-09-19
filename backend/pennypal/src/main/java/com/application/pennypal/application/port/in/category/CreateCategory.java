package com.application.pennypal.application.port.in.category;

import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryRequestDTO;

public interface CreateCategory {
    Category execute(CategoryRequestDTO category, String userId);
}
