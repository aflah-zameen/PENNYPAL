package com.application.pennypal.application.usecases.admin;

import com.application.pennypal.application.output.paged.PagedResultOutput;
import com.application.pennypal.application.output.user.UserFiltersOutput;
import com.application.pennypal.domain.entity.User;

public interface FetchUsers {
    PagedResultOutput<User> execute(UserFiltersOutput userFiltersDTO, int size, int page, String keyword);
}
