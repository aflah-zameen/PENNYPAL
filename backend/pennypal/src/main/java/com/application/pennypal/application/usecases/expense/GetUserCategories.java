package com.application.pennypal.application.usecases.expense;

import com.application.pennypal.application.dto.CategoryUserResponseDTO;

import java.util.List;

public interface GetUserCategories {
    List<CategoryUserResponseDTO> get();
}
