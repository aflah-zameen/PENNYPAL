package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.port.UpdateUserPort;
import com.application.pennypal.application.service.user.UpdateUserService;
import com.application.pennypal.application.usecases.user.UpdateUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfig {


    @Bean
    public UpdateUser updateUser(UpdateUserPort updateUserPort){
        return new UpdateUserService(updateUserPort);
    }
}
