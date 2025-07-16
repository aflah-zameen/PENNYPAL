package com.application.pennypal.application.usecases.category;

import com.application.pennypal.application.output.category.CategoryUserOutput;

public interface GetCategoryById {
    CategoryUserOutput get(Long categoryId);
}
