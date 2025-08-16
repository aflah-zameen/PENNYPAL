package com.application.pennypal.application.port.in.category;

import com.application.pennypal.domain.catgeory.entity.Category;

public interface CreateCategory {
    Category execute(Category category,String userId);
}
