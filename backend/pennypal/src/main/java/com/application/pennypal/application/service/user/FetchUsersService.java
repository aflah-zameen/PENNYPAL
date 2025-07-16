package com.application.pennypal.application.service.user;

import com.application.pennypal.application.output.paged.PagedResultOutput;
import com.application.pennypal.application.output.user.UserFiltersOutput;
import com.application.pennypal.application.port.FetchFilteredUsersPort;
import com.application.pennypal.application.usecases.admin.FetchUsers;
import com.application.pennypal.domain.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FetchUsersService implements FetchUsers {
    private final FetchFilteredUsersPort fetchFilteredUsersPort;
    @Override
    public PagedResultOutput<User> execute(UserFiltersOutput userFiltersOutput,
                                           int page, int size, String keyword){
        return fetchFilteredUsersPort.execute(userFiltersOutput,page,size,keyword);
    }
}
