package com.application.pennypal.application.usecases.expense;

import com.application.pennypal.application.output.category.CategoryUserOutput;

import java.util.List;

public interface GetUserCategories {
    List<CategoryUserOutput> get();
}
