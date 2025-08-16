package com.application.pennypal.application.port.out.service;

import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.application.dto.output.user.UserFiltersOutput;
import com.application.pennypal.domain.user.entity.User;

public interface FetchFilteredUsersPort {
    PagedResultOutput<User> execute(UserFiltersOutput userFiltersDTO, int page, int size, String keyword);
}
