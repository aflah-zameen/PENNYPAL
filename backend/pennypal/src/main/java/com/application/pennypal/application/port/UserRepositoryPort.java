package com.application.pennypal.application.port;

import com.application.pennypal.application.dto.PagedResult;
import com.application.pennypal.application.dto.UserFiltersDTO;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
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
    PagedResult<User> findAllFiltered(UserFiltersDTO userFiltersDTO,int page,int size,String keyword);
}
