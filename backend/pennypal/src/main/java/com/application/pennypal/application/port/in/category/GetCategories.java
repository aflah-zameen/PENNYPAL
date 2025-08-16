package com.application.pennypal.application.port.in.category;

import com.application.pennypal.domain.catgeory.entity.Category;

import java.util.List;

public interface GetCategories {
    List<Category> execute();
}
