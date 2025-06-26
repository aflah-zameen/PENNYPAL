package com.application.pennypal.application.dto;

import com.application.pennypal.domain.user.valueObject.UserDomainDTO;

public record LoginResponseDTO(UserDomainDTO user,String accessToken,String refreshToken) {
}
