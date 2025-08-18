package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.lend.SendLendingRequest;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.application.service.lend.SendLendingRequestService;
import com.application.pennypal.application.service.lend.UpdateLendingRequestStatusService;
import com.application.pennypal.infrastructure.adapter.out.lend.UpdateLendingRequestStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LendingBeanConfig {

    @Bean
    public SendLendingRequest sendLendingRequest(LendingRequestRepositoryPort lendingRequestRepositoryPort,
                                                 UserRepositoryPort userRepositoryPort,
                                                 MessageBrokerPort messageBrokerPort){
        return new SendLendingRequestService(
                lendingRequestRepositoryPort,
                userRepositoryPort,
                messageBrokerPort
                );
    }

    @Bean
    public UpdateLendingRequestStatus updateLendingRequestStatus(LendingRequestRepositoryPort lendingRequestRepositoryPort,
                                                                 UserRepositoryPort userRepositoryPort,
                                                                 MessageBrokerPort messageBrokerPort){
        return new UpdateLendingRequestStatusService(lendingRequestRepositoryPort,messageBrokerPort,userRepositoryPort);
    }
}
