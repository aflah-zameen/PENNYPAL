package com.application.pennypal.application.port.in.admin;

import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.application.dto.output.user.UserFiltersOutput;
import com.application.pennypal.domain.user.entity.User;

public interface FetchUsers {
    PagedResultOutput<User> execute(UserFiltersOutput userFiltersDTO, int size, int page, String keyword);
}
