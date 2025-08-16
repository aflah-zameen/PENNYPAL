package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.application.dto.output.user.ContactOutputModel;
import com.application.pennypal.application.dto.output.user.UserFiltersOutput;
import com.application.pennypal.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    User save(User user);
    User update(User user);
    boolean existsByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);
    Optional<User> findByRefreshToken(String refreshToken);
    void deleteRefreshToken(String refreshToken);
    PagedResultOutput<User> findAllFiltered(UserFiltersOutput userFiltersDTO, int page, int size, String keyword);
    Optional<User> findByUserId(String userId);
    List<User> getAllContacts(String userId);
}
