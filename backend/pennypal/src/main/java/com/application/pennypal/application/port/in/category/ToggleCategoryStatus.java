package com.application.pennypal.application.port.in.category;

import com.application.pennypal.domain.catgeory.entity.Category;

public interface ToggleCategoryStatus {
    Category toggle(String categoryId,String userId);
}
