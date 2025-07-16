package com.application.pennypal.application.usecases.category;

import com.application.pennypal.domain.entity.Category;

public interface CreateCategory {
    Category execute(Category category,Long userId);
}
