package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.admin.CreateAdmin;
import com.application.pennypal.application.port.in.admin.SuspendUser;
import com.application.pennypal.application.port.in.admin.UpdateAdmin;
import com.application.pennypal.application.port.in.auth.VerifyEmailWithToken;
import com.application.pennypal.application.port.in.lent.UpdateLoanCaseAction;
import com.application.pennypal.application.port.in.sale.SubscriptionAnalytics;
import com.application.pennypal.application.port.out.config.AuthConfigurationPort;
import com.application.pennypal.application.port.out.event.AdminDomainEventPublisher;
import com.application.pennypal.application.port.out.repository.*;
import com.application.pennypal.application.port.out.service.*;
import com.application.pennypal.application.service.admin.CreateAdminService;
import com.application.pennypal.application.service.admin.UpdateAdminService;
import com.application.pennypal.application.service.auth.ValidateEmailUniqueness;
import com.application.pennypal.application.service.auth.VerifyEmailWithTokenService;
import com.application.pennypal.application.service.lent.SuspendUserService;
import com.application.pennypal.application.service.lent.UpdateLoanCaseActionService;
import com.application.pennypal.application.service.sale.SubscriptionAnalyticsService;
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
    public UpdateAdmin updateAdmin(UserRepositoryPort userRepositoryPort,
                                   FileUploadPort fileUploadPort){
        return new UpdateAdminService(userRepositoryPort,fileUploadPort);
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

    @Bean
    public SuspendUser suspendUser(UserRepositoryPort userRepositoryPort,
                                   MessageBrokerPort messageBrokerPort){
        return new SuspendUserService(userRepositoryPort,messageBrokerPort);
    }

    @Bean
    public UpdateLoanCaseAction updateLoanCaseAction(LoanCaseRepositoryPort loanCaseRepositoryPort){
        return new UpdateLoanCaseActionService(loanCaseRepositoryPort);
    }

    @Bean
    public SubscriptionAnalytics subscriptionAnalytics(SubscriptionPlanRepositoryPort subscriptionPlanRepositoryPort,
                                                       UserSubscriptionRepositoryPort userSubscriptionRepositoryPort,
                                                       TransactionRepositoryPort transactionRepositoryPort){
        return new SubscriptionAnalyticsService(subscriptionPlanRepositoryPort,transactionRepositoryPort,userSubscriptionRepositoryPort);
    }
}
