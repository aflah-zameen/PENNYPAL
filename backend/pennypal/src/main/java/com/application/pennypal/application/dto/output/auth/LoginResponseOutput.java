package com.application.pennypal.application.dto.output.auth;

import com.application.pennypal.domain.valueObject.UserDomainDTO;

public record LoginResponseOutput(UserDomainDTO user, String accessToken, String refreshToken) {
}
