package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.port.FetchFilteredUsersPort;
import com.application.pennypal.application.service.FetchUsersService;
import com.application.pennypal.application.usecases.admin.FetchUsers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminBeanConfig {
    @Bean
    public FetchUsers fetchUsers(FetchFilteredUsersPort fetchFilteredUsersPort){
        return new FetchUsersService(fetchFilteredUsersPort);
    }
}
