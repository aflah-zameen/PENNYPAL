package com.application.pennypal.application.service;

import com.application.pennypal.application.dto.LoginResponseDTO;
import com.application.pennypal.application.port.RefreshTokenServicePort;
import com.application.pennypal.application.port.TokenServicePort;
import com.application.pennypal.application.port.UserAuthenticationPort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.application.usecases.user.LoginUser;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.validator.EmailValidator;
import com.application.pennypal.domain.user.valueObject.UserDomainDTO;
import com.application.pennypal.shared.exception.InvalidPasswordArgumentException;
import com.application.pennypal.shared.exception.UserNotFoundException;

public class LoginService implements LoginUser {
    private final UserAuthenticationPort userAuthenticationPort;
    private final UserRepositoryPort userRepositoryPort;
    private final TokenServicePort tokenServicePort;
    private final RefreshTokenServicePort refreshTokenServicePort;

    public LoginService(UserAuthenticationPort userAuthenticationPort,UserRepositoryPort userRepositoryPort,
              TokenServicePort tokenServicePort,RefreshTokenServicePort refreshTokenServicePort){
        this.refreshTokenServicePort = refreshTokenServicePort;
        this.tokenServicePort = tokenServicePort;
        this.userAuthenticationPort = userAuthenticationPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public LoginResponseDTO execute(String email, String password, String ipAddress) {
        if(password.trim().isEmpty() || password.length()<8){
            throw new InvalidPasswordArgumentException("Password is null or invalid");
        }
        EmailValidator.validate(email);

        userAuthenticationPort.authenticate(email,password);
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        String accessToken =  tokenServicePort.generateAccessToken(user);
        String refreshToken = refreshTokenServicePort.generateRefreshToken(user.getId(),ipAddress);
        UserDomainDTO userDomainDTO = new UserDomainDTO(user.getId(), user.getName(),user.getEmail(),user.getRoles(),
                user.getPhone(), user.isActive(),user.isVerified(),user.getCreatedAt(),user.getUpdatedAt(),user.getProfileURL());
        return new LoginResponseDTO(userDomainDTO,accessToken,refreshToken);
    }
}
