package com.application.pennypal.application.usecases.category;

import com.application.pennypal.domain.entity.Category;

public interface ToggleCategoryStatus {
    Category toggle(Long categoryId,Long userId);
}
