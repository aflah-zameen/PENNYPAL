package com.application.pennypal.application.port;

import com.application.pennypal.application.dto.PagedResult;
import com.application.pennypal.application.dto.UserFiltersDTO;
import com.application.pennypal.domain.user.entity.User;

public interface FetchFilteredUsersPort {
    PagedResult<User> execute(UserFiltersDTO userFiltersDTO, int page, int size,String keyword);
}
