package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.domain.user.valueObject.IncomeDTO;

import java.util.List;

public interface GetAllIncomes {
    List<IncomeDTO> get(Long userId);
}
