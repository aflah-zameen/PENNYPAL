package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.domain.entity.Income;

import java.util.List;

public interface GetRecentIncomes {
    List<Income> get(Long userId,int size);
}
