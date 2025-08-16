package com.application.pennypal.infrastructure.config.eventConfig;

import com.application.pennypal.application.port.out.event.AdminDomainEventPublisher;
import com.application.pennypal.application.port.out.event.UserDomainEventPublisher;
import com.application.pennypal.domain.user.event.AdminRegisteredEvent;
import com.application.pennypal.domain.user.event.UserRegisteredEvent;
import com.application.pennypal.infrastructure.event.publisher.SpringDomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventPublisherConfig {
    @Bean
    public UserDomainEventPublisher userDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher){
        return new SpringDomainEventPublisher<UserRegisteredEvent>(applicationEventPublisher)::publish;
    }

    @Bean
    public AdminDomainEventPublisher adminDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher){
        return new SpringDomainEventPublisher<AdminRegisteredEvent>(applicationEventPublisher)::publish;
    }
}
