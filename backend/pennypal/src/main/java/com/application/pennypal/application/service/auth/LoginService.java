package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.dto.output.auth.LoginResponseOutput;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.usecase.auth.UserSuspendedApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.out.service.TokenServicePort;
import com.application.pennypal.application.port.out.service.UserAuthenticationPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.in.user.LoginUser;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.exception.validation.InvalidPasswordDomainException;
import com.application.pennypal.domain.user.validator.EmailValidator;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginService implements LoginUser {
    private final UserAuthenticationPort userAuthenticationPort;
    private final UserRepositoryPort userRepositoryPort;
    private final TokenServicePort tokenServicePort;
    private final GenerateRefreshTokenPort generateRefreshTokenPort;
    private final HybridRefreshTokenService hybridRefreshTokenService;

    @Override
    public LoginResponseOutput execute(String email, String password, String ipAddress) {
        if(password.trim().isEmpty() || password.length()<8){
            throw new InvalidPasswordDomainException("Password is null or invalid");
        }
        EmailValidator.validate(email);

        userAuthenticationPort.authenticate(email,password);
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(()->new UserNotFoundApplicationException("User not found"));

        if(user.isSuspended()){
            throw new UserSuspendedApplicationException("User account has been suspended");
        }

        /// Generate tokens
        String accessToken =  tokenServicePort.generateAccessToken(user);
        String refreshToken = generateRefreshTokenPort.get();

        /// Save refresh token
        hybridRefreshTokenService.save(user.getUserId(), refreshToken,ipAddress);

        UserDomainDTO userDomainDTO = new UserDomainDTO(user.getUserId(), user.getName(),user.getEmail(),user.getRoles(),
                user.getPhone(), user.isActive(),user.isVerified(),user.getCreatedAt() ,
                user.getProfileURL().isPresent() ? user.getProfileURL().get() : null);
        return new LoginResponseOutput(userDomainDTO,accessToken,refreshToken);
    }
}
