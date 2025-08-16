package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.auth.SendVerificationEmail;
import com.application.pennypal.application.port.in.user.*;
import com.application.pennypal.application.port.out.config.AuthConfigurationPort;
import com.application.pennypal.application.port.out.event.UserDomainEventPublisher;
import com.application.pennypal.application.port.out.repository.WalletRepositoryPort;
import com.application.pennypal.application.port.out.service.FileUploadPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.*;
import com.application.pennypal.application.service.auth.*;
import com.application.pennypal.application.service.user.CreateUserService;
import com.application.pennypal.application.service.user.GetUserService;
import com.application.pennypal.infrastructure.adapter.out.auth.JpaRefreshTokenServiceAdapter;
import com.application.pennypal.infrastructure.adapter.out.auth.RedisRefreshTokenServiceAdapter;
import com.application.pennypal.infrastructure.config.properties.JwtProperties;
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
            GenerateRefreshTokenPort generateRefreshTokenPort,
            HybridRefreshTokenService hybridRefreshTokenService){
        return new LoginService(userAuthenticationPort,userRepositoryPort,tokenServicePort,generateRefreshTokenPort,hybridRefreshTokenService);
    }

    @Bean
    public RefreshAccessToken refreshAccessToken(TokenServicePort tokenServicePort, HybridRefreshTokenService hybridRefreshTokenService, UserRepositoryPort userRepositoryPort,GenerateRefreshTokenPort generateRefreshTokenPort){
        return new RefreshAccessTokenService(
                hybridRefreshTokenService,
                tokenServicePort,
                generateRefreshTokenPort,
                userRepositoryPort
        );
    }

    @Bean
    public CreateUser createUser(UserRepositoryPort userRepositoryPort, EncodePasswordPort encodePasswordPort,
                                 ValidateEmailUniqueness validateEmailUniqueness, UserDomainEventPublisher userDomainEventPublisher,
                                 FileUploadPort fileUploadPort,
                                 AuthConfigurationPort authConfigurationPort){
        return new CreateUserService(userRepositoryPort,encodePasswordPort,validateEmailUniqueness, fileUploadPort,userDomainEventPublisher,authConfigurationPort);
    }

    @Bean
    public ValidateEmailUniqueness validateEmailUniqueness(UserRepositoryPort userRepositoryPort){
        return new  ValidateEmailUniqueness(userRepositoryPort);
    }

    @Bean
    public VerifyOtp verifyOtp(OtpCachePort otpCachePort, UserRepositoryPort userRepositoryPort,
                               WalletRepositoryPort walletRepositoryPort){
        return new VerifyOtpService(otpCachePort,userRepositoryPort,walletRepositoryPort);
    }

    @Bean
    public GetUser getUser(TokenServicePort tokenServicePort, UserRepositoryPort userRepositoryPort){
        return new GetUserService(tokenServicePort,userRepositoryPort);
    }

    @Bean
    public ResetPassword updatePassword(UserRepositoryPort userRepositoryPort, EncodePasswordPort encodePasswordPort,VerificationTokenServicePort verificationTokenServicePort){
        return new ResetPasswordService(userRepositoryPort,encodePasswordPort,verificationTokenServicePort);
    }

    @Bean
    public LogoutUser logoutUser(TokenBlackListPort tokenBlackListPort,
                                 HybridRefreshTokenService hybridRefreshTokenService,
                                 TokenServicePort tokenServicePort){
        return new LogoutUserService(tokenBlackListPort,hybridRefreshTokenService,tokenServicePort);
    }

    @Bean
    public  SendVerificationEmail sendVerificationEmail(UserRepositoryPort userRepositoryPort,
                                                        EmailSendPort emailSendPort,
                                                        VerificationTokenServicePort verificationTokenServicePort
                                                        ){
        return new SendVerificationEmailService(userRepositoryPort,emailSendPort, verificationTokenServicePort);
    }

    @Bean
    public HybridRefreshTokenService hybridRefreshTokenService(RedisRefreshTokenServiceAdapter redisPort, JpaRefreshTokenServiceAdapter dbPort){
        return new HybridRefreshTokenService(redisPort,dbPort);
    }
}
