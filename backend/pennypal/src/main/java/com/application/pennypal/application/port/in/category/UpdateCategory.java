package com.application.pennypal.application.port.in.category;

import com.application.pennypal.domain.catgeory.entity.Category;

public interface UpdateCategory {
    Category update(Category category,String categoryId,String userId);
}
