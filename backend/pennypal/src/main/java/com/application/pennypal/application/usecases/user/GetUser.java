package com.application.pennypal.application.usecases.user;

import com.application.pennypal.domain.user.valueObject.UserDomainDTO;

public interface GetUser {
    UserDomainDTO get(String accessToken);
}
