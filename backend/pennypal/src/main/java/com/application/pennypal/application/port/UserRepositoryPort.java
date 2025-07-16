package com.application.pennypal.application.port;

import com.application.pennypal.application.output.paged.PagedResultOutput;
import com.application.pennypal.application.output.user.UserFiltersOutput;
import com.application.pennypal.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    User save(User user);
    boolean existsByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);
    Optional<User> findByRefreshToken(String refreshToken);
    void deleteRefreshToken(String refreshToken);
    PagedResultOutput<User> findAllFiltered(UserFiltersOutput userFiltersDTO, int page, int size, String keyword);
}
