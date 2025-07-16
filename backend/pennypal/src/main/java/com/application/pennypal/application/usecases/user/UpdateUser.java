package com.application.pennypal.application.usecases.user;

import com.application.pennypal.application.output.user.UserUpdateApplicationOutput;
import com.application.pennypal.domain.valueObject.UserDomainDTO;

public interface UpdateUser {
    UserDomainDTO update(UserUpdateApplicationOutput user, String token);
}
