package com.application.pennypal.application.port.in.user;

import com.application.pennypal.domain.valueObject.UserDomainDTO;

public interface GetUser {
    UserDomainDTO get(String accessToken);
}
