package com.application.pennypal.application.usecases.admin;

import com.application.pennypal.application.dto.PagedResult;
import com.application.pennypal.application.dto.UserFiltersDTO;
import com.application.pennypal.domain.user.entity.User;

import java.util.List;

public interface FetchUsers {
    PagedResult<User> execute(UserFiltersDTO userFiltersDTO, int size, int page,String keyword);
}
