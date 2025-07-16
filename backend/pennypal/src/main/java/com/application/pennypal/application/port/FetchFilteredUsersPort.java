package com.application.pennypal.application.port;

import com.application.pennypal.application.output.paged.PagedResultOutput;
import com.application.pennypal.application.output.user.UserFiltersOutput;
import com.application.pennypal.domain.entity.User;

public interface FetchFilteredUsersPort {
    PagedResultOutput<User> execute(UserFiltersOutput userFiltersDTO, int page, int size, String keyword);
}
