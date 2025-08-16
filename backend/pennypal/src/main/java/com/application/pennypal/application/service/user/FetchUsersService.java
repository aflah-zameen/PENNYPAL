package com.application.pennypal.application.service.user;

import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.application.dto.output.user.UserFiltersOutput;
import com.application.pennypal.application.port.out.service.FetchFilteredUsersPort;
import com.application.pennypal.application.port.in.admin.FetchUsers;
import com.application.pennypal.domain.user.entity.User;
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
