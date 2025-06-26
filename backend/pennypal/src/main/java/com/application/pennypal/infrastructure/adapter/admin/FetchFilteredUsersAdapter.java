package com.application.pennypal.infrastructure.adapter.admin;

import com.application.pennypal.application.dto.PagedResult;
import com.application.pennypal.application.dto.UserFiltersDTO;
import com.application.pennypal.application.port.FetchFilteredUsersPort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FetchFilteredUsersAdapter implements FetchFilteredUsersPort {
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public PagedResult<User> execute(UserFiltersDTO userFiltersDTO, int page, int size,String keyword) {
        return userRepositoryPort.findAllFiltered(userFiltersDTO,page,size,keyword);
    }
}
