package com.application.pennypal.infrastructure.persistence.jpa.user;

import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.application.dto.output.user.ContactOutputModel;
import com.application.pennypal.application.dto.output.user.TransferTransaction;
import com.application.pennypal.application.dto.output.user.UserFiltersOutput;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.infrastructure.cache.service.CacheService;
import com.application.pennypal.infrastructure.cache.user.CachedUserDto;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.UserJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository springDataUserRepository;
    private final CacheService cacheService;

    @Override
    public User save(User user){
        UserEntity savedUser= springDataUserRepository.save(UserJpaMapper.toEntity(user));
        return  UserJpaMapper.toDomain(savedUser);
    }

    @Override
    @CacheEvict(value = "user_cache",key = "#user.email")
    public User update(User user){
        UserEntity userEntity = springDataUserRepository.findByUserId(user.getUserId())
                .orElseThrow(()-> new UserNotFoundApplicationException("User not found"));
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setPassword(user.getPassword());
        userEntity.setActive(user.isActive());
        userEntity.setVerified(user.isVerified());
        userEntity.setProfileURL(user.getProfileURL().isPresent() ? user.getProfileURL().get() : null);
        UserEntity updatedUser = springDataUserRepository.save(userEntity);
        return UserJpaMapper.toDomain(updatedUser);
    }
    @Override
    public Optional<User> findByEmail(String email){
        CachedUserDto cachedDto = cacheService.findCachedUserDtoByEmail(email);
        return Optional.ofNullable(cachedDto).map(CachedUserDto::toDomain);
    }


    @Override
    public boolean existsByEmail(String email){
        return springDataUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id){
        return springDataUserRepository.findById(id)
                .map(UserJpaMapper::toDomain);
    }

    @Override
    public List<User> findAll(){
        return springDataUserRepository.findAll()
                .stream().map(UserJpaMapper::toDomain).toList();
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
    public PagedResultOutput<User> findAllFiltered(UserFiltersOutput userFiltersDTO, int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page,size);
        Specification<UserEntity> spec = UserSpecification.hasRole(userFiltersDTO.role() != null ? userFiltersDTO.role().toString() : null )
                .and(UserSpecification.keywordLike(keyword))
                .and(UserSpecification.hasStatus(userFiltersDTO.status()))
                .and(UserSpecification.joinedAfter(userFiltersDTO.joinedAfter()))
                .and(UserSpecification.joinedBefore(userFiltersDTO.joinedBefore()));
        Page<UserEntity> userEntityPage = springDataUserRepository.findAll(spec,pageable);
        List<User> userList = userEntityPage.getContent().stream()
                .map(UserJpaMapper::toDomain).toList();
        return new PagedResultOutput<>(userList,userEntityPage.getNumber(),userEntityPage.getSize(),
                userEntityPage.getTotalElements(),userEntityPage.getTotalPages());
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return springDataUserRepository.findByUserId(userId).map(UserJpaMapper::toDomain);
    }

    @Override
    public List<User> getAllContacts(String userId) {
        return springDataUserRepository.findAllContacts(userId, Roles.USER).stream()
                .map(UserJpaMapper::toDomain)
                .toList();
    }

}
