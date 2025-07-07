package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.port.*;
import com.application.pennypal.application.service.auth.*;
import com.application.pennypal.application.service.user.CreateUserService;
import com.application.pennypal.application.service.user.GetUserService;
import com.application.pennypal.application.usecases.user.*;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.RefreshToken.RefreshTokenRepository;
import com.application.pennypal.infrastructure.security.jwt.JwtProperties;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class AuthConfig {


    @Bean
    public SecretKey accessToken(JwtProperties props){
        SecretKey key = Keys.hmacShaKeyFor(props.accessSecret().getBytes(StandardCharsets.UTF_8));
        Assert.isTrue(key.getEncoded().length >= 32,"JWT secret must be at least 256 bits");
        return key;
    }

    @Bean
    public LoginUser loginUser(
            UserAuthenticationPort userAuthenticationPort,
            UserRepositoryPort userRepositoryPort,
            TokenServicePort tokenServicePort,
            RefreshTokenServicePort refreshTokenServicePort
    ){
        return new LoginService(userAuthenticationPort,userRepositoryPort,tokenServicePort,refreshTokenServicePort);
    }

    @Bean
    public RefreshAccessToken refreshAccessToken(TokenServicePort tokenServicePort,RefreshTokenServicePort refreshTokenServicePort,UserRepositoryPort userRepositoryPort){
        return new RefreshAccessTokenService(
                refreshTokenServicePort,
                userRepositoryPort,
                tokenServicePort
        );
    }

    @Bean
    public CreateUser createUser(UserRepositoryPort userRepositoryPort, EncodePasswordPort encodePasswordPort,
                                 ValidateEmailUniqueness validateEmailUniqueness,
                                 S3SystemPort s3SystemPort){
        return new CreateUserService(userRepositoryPort,encodePasswordPort,validateEmailUniqueness,s3SystemPort);
    }

    @Bean
    public ValidateEmailUniqueness validateEmailUniqueness(UserRepositoryPort userRepositoryPort){
        return new  ValidateEmailUniqueness(userRepositoryPort);
    }

    @Bean
    public VerifyOtp verifyOtp(OtpServicePort otpServicePort){
        return new VerifyOtpService(otpServicePort);
    }

    @Bean
    public GetUser getUser(TokenServicePort tokenServicePort,UserRepositoryPort userRepositoryPort){
        return new GetUserService(tokenServicePort,userRepositoryPort);
    }

    @Bean
    public UpdatePassword updatePassword(UserRepositoryPort userRepositoryPort,EncodePasswordPort encodePasswordPort){
        return new UpdatePasswordService(userRepositoryPort,encodePasswordPort);
    }

    @Bean
    public LogoutUser logoutUser(RefreshTokenRepository refreshTokenRepository){
        return new LogoutUserService(refreshTokenRepository);
    }
}
