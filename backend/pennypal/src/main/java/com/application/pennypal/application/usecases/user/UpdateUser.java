package com.application.pennypal.application.usecases.user;

import com.application.pennypal.application.dto.UserUpdateApplicationDTO;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.UpdateUserRequest;

public interface UpdateUser {
    UserDomainDTO update(UserUpdateApplicationDTO user, String token);
}
