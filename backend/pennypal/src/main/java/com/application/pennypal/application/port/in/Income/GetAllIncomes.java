package com.application.pennypal.application.port.in.Income;

import com.application.pennypal.domain.entity.Income;

import java.util.List;

public interface GetAllIncomes {
    List<Income> get(Long userId);
}
