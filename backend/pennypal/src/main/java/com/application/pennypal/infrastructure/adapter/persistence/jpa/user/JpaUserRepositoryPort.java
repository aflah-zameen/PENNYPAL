package com.application.pennypal.infrastructure.adapter.persistence.jpa.user;

import com.application.pennypal.application.dto.PagedResult;
import com.application.pennypal.application.dto.UserFiltersDTO;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.UserMapper;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepositoryPort implements UserRepositoryPort {

    private final SpringDataUserRepository springDataUserRepository;
    private final UserMapper userMapper;
    public JpaUserRepositoryPort(SpringDataUserRepository springDataUserRepository,
                                 UserMapper userMapper,
                                 UserSpecification userSpecification){
        this.springDataUserRepository = springDataUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user){
        UserEntity userEntity= springDataUserRepository.save(userMapper.toEntity(user));
        return  userMapper.toDomain(userEntity);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return springDataUserRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email){
        return springDataUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id){
        return springDataUserRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public List<User> findAll(){
        return springDataUserRepository.findAll()
                .stream().map(userMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<User> findByRefreshToken(String refreshToken) {
        return Optional.empty();
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {

    }

    @Override
    public PagedResult<User> findAllFiltered(UserFiltersDTO userFiltersDTO,int page,int size,String keyword) {
        Pageable pageable = PageRequest.of(page,size);
        Specification<UserEntity> spec = UserSpecification.hasRole(userFiltersDTO.role() != null ? userFiltersDTO.role().toString() : null )
                .and(UserSpecification.keywordLike(keyword))
                .and(UserSpecification.hasStatus(userFiltersDTO.status()))
                .and(UserSpecification.joinedAfter(userFiltersDTO.joinedAfter()))
                .and(UserSpecification.joinedBefore(userFiltersDTO.joinedBefore()));
        Page<UserEntity> userEntityPage = springDataUserRepository.findAll(spec,pageable);
        List<User> userList = userEntityPage.getContent().stream()
                .map(userMapper::toDomain).toList();
        return new PagedResult<>(userList,userEntityPage.getNumber(),userEntityPage.getSize(),
                userEntityPage.getTotalElements(),userEntityPage.getTotalPages());
    }
}
