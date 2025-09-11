package com.application.pennypal.infrastructure.cache.user;

import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.Roles;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
public class CachedUserDto implements Serializable {

        private String userId;
        private String name;
        private String email;
        private String password;
        private String phone;
        private Set<Roles> roles;
        private boolean verified;
        private boolean active;
        private boolean isSuspended;
        private String profileURL;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime updatedAt;

        public CachedUserDto() {}

        public CachedUserDto(String userId, String name, String email, String password, String phone,
                             Set<Roles> roles, boolean verified, boolean active,boolean isSuspended,
                             String profileURL, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.password = password;
            this.phone = phone;
            this.roles = roles;
            this.verified = verified;
            this.active = active;
            this.isSuspended = isSuspended;
            this.profileURL = profileURL;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        // 🔁 Map from domain entity to cache DTO
        public static CachedUserDto fromDomain(User user) {
            return new CachedUserDto(
                    user.getUserId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPhone(),
                    user.getRoles(),
                    user.isVerified(),
                    user.isActive(),
                    user.isSuspended(),
                    user.getProfileURL().isPresent() ? user.getProfileURL().get() : null, // Assuming profileURL is String in domain
                    user.getCreatedAt(),
                    user.getUpdatedAt().isPresent() ? user.getUpdatedAt().get() : null
            );
        }

        // 🔁 Map from cache DTO back to domain entity
        public User toDomain() {
            return User.reconstruct(
                    userId,
                    name,
                    email,
                    password,
                    phone,
                    roles,
                    active,
                    verified,
                    isSuspended,
                    createdAt,
                    updatedAt,
                    profileURL
            );
        }

    }

