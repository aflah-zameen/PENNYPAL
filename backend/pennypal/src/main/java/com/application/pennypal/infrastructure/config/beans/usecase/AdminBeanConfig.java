package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.admin.CreateAdmin;
import com.application.pennypal.application.port.in.auth.VerifyEmailWithToken;
import com.application.pennypal.application.port.out.config.AuthConfigurationPort;
import com.application.pennypal.application.port.out.event.AdminDomainEventPublisher;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.application.port.out.service.FetchFilteredUsersPort;
import com.application.pennypal.application.port.out.service.VerificationTokenServicePort;
import com.application.pennypal.application.service.admin.CreateAdminService;
import com.application.pennypal.application.service.auth.ValidateEmailUniqueness;
import com.application.pennypal.application.service.auth.VerifyEmailWithTokenService;
import com.application.pennypal.application.service.user.FetchUsersService;
import com.application.pennypal.application.port.in.admin.FetchUsers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminBeanConfig {
    @Bean
    public FetchUsers fetchUsers(FetchFilteredUsersPort fetchFilteredUsersPort){
        return new FetchUsersService(fetchFilteredUsersPort);
    }

    @Bean
    public VerifyEmailWithToken verifyAdminEmail(VerificationTokenServicePort verificationTokenServicePort,
                                                 UserRepositoryPort userRepositoryPort){
        return new VerifyEmailWithTokenService(verificationTokenServicePort,userRepositoryPort);
    }

    @Bean
    public CreateAdmin createAdmin(ValidateEmailUniqueness validateEmailUniqueness,
                                   EncodePasswordPort encodePasswordPort,
                                   UserRepositoryPort userRepositoryPort,
                                   AdminDomainEventPublisher adminDomainEventPublisher,
                                   AuthConfigurationPort authConfigurationPort){
        return new CreateAdminService(validateEmailUniqueness,encodePasswordPort,userRepositoryPort,adminDomainEventPublisher,authConfigurationPort);
    }
}
