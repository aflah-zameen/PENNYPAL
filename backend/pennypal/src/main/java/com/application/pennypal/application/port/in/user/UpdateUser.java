package com.application.pennypal.application.port.in.user;

import com.application.pennypal.application.dto.output.user.UserUpdateApplicationOutput;
import com.application.pennypal.domain.valueObject.UserDomainDTO;

public interface UpdateUser {
    UserDomainDTO update(UserUpdateApplicationOutput user, String token);
}
