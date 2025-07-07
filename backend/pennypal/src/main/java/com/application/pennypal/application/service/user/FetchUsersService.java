package com.application.pennypal.application.service.user;

import com.application.pennypal.application.dto.PagedResult;
import com.application.pennypal.application.dto.UserFiltersDTO;
import com.application.pennypal.application.port.FetchFilteredUsersPort;
import com.application.pennypal.application.usecases.admin.FetchUsers;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FetchUsersService implements FetchUsers {
    private final FetchFilteredUsersPort fetchFilteredUsersPort;
    @Override
    public PagedResult<User> execute(UserFiltersDTO userFiltersDTO,
                                     int page,int size,String keyword){
        return fetchFilteredUsersPort.execute(userFiltersDTO,page,size,keyword);
    }
}
