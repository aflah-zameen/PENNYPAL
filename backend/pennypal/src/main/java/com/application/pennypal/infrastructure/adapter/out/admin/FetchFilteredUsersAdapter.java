package com.application.pennypal.infrastructure.adapter.out.admin;

import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.application.dto.output.user.UserFiltersOutput;
import com.application.pennypal.application.port.out.service.FetchFilteredUsersPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.user.entity.User;
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
