package com.application.pennypal.application.usecases.user;

import com.application.pennypal.domain.valueObject.UserDomainDTO;

public interface GetUser {
    UserDomainDTO get(String accessToken);
}
