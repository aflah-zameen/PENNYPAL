package com.application.pennypal.interfaces.rest.dtos.auth;

import com.application.pennypal.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtData {
    private User user;
    private String AccessToken;
    private String RefreshToken;
}
