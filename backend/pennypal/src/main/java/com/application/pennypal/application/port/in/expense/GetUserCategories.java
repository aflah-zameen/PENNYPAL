package com.application.pennypal.application.port.in.expense;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;

import java.util.List;

public interface GetUserCategories {
    List<CategoryUserOutput> get();
}
