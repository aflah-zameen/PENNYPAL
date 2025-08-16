package com.application.pennypal.application.port.in.category;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;

public interface GetCategoryById {
    CategoryUserOutput get(String categoryId);
}
