package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.subscription.AddSubscriptionPlan;
import com.application.pennypal.application.port.in.subscription.DeleteSubscriptionPlan;
import com.application.pennypal.application.port.in.subscription.EditSubscriptionPlan;
import com.application.pennypal.application.port.in.subscription.PurchasePlan;
import com.application.pennypal.application.port.out.repository.SubscriptionPlanRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserSubscriptionRepositoryPort;
import com.application.pennypal.application.service.subscription.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionPlanBeanConfig {
    @Bean
    public AddSubscriptionPlan addSubscriptionPlan(SubscriptionPlanRepositoryPort repositoryPort){
        return new AddSubscriptionPlanService(repositoryPort);
    }

    @Bean
    public DeleteSubscriptionPlan deleteSubscriptionPlan(SubscriptionPlanRepositoryPort repositoryPort){
        return new DeleteSubscriptionPlanService(repositoryPort);
    }

    @Bean
    public EditSubscriptionPlan editSubscriptionPlan(SubscriptionPlanRepositoryPort repositoryPort){
        return new EditSubscriptionPlanService(repositoryPort);
    }

    @Bean
    public HasActiveSubscriptionService hasActiveSubscriptionService(UserSubscriptionRepositoryPort subscriptionRepositoryPort,
                                                                     UserRepositoryPort userRepositoryPort){
        return new HasActiveSubscriptionService(subscriptionRepositoryPort,userRepositoryPort);
    }

    @Bean
    public PurchasePlan purchasePlan(SubscriptionPlanRepositoryPort planRepositoryPort,
                                     UserSubscriptionRepositoryPort subscriptionRepositoryPort
                                     ){
        return new PurchasePlanService(planRepositoryPort,subscriptionRepositoryPort);
    }

}
