package com.application.pennypal.application.usecases.user;

import com.application.pennypal.domain.valueObject.TokenPairDTO;

public interface RefreshAccessToken {
    TokenPairDTO execute(String refreshToken,String ipAddress);
}
