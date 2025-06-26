package com.application.pennypal.interfaces.rest.dtos;

import com.application.pennypal.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtData {
    private User user;
    private String AccessToken;
    private String RefreshToken;
}
