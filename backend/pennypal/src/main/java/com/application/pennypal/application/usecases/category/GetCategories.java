package com.application.pennypal.application.usecases.category;

import com.application.pennypal.domain.user.entity.Category;

import java.util.List;

public interface GetCategories {
    List<Category> execute();
}
