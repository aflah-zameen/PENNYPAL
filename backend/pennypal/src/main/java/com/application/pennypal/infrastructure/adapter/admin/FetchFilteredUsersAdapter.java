package com.application.pennypal.infrastructure.adapter.admin;

import com.application.pennypal.application.output.paged.PagedResultOutput;
import com.application.pennypal.application.output.user.UserFiltersOutput;
import com.application.pennypal.application.port.FetchFilteredUsersPort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FetchFilteredUsersAdapter implements FetchFilteredUsersPort {
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public PagedResultOutput<User> execute(UserFiltersOutput userFiltersDTO, int page, int size, String keyword) {
        return userRepositoryPort.findAllFiltered(userFiltersDTO,page,size,keyword);
    }
}
