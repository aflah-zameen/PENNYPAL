package com.application.pennypal.application.usecases.category;

import com.application.pennypal.domain.entity.Category;

public interface UpdateCategory {
    Category update(Category category,Long categoryId,Long userId);
}
