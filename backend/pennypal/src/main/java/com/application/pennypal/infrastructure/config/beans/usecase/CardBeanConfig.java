package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.mappers.transaction.TransactionApplicationMapper;
import com.application.pennypal.application.port.in.card.*;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.application.service.card.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardBeanConfig {
    @Bean
    GetCard getCard(CardRepositoryPort cardRepositoryPort, TransactionRepositoryPort transactionRepositoryPort){
        return new GetCardService(cardRepositoryPort,transactionRepositoryPort);
    }

    @Bean
    AddCard addCard(CardRepositoryPort cardRepositoryPort,
                    EncodePasswordPort encodePasswordPort){
        return new AddCardService(cardRepositoryPort,encodePasswordPort);
    }

    @Bean
    GetCardSpendingOverview getCardSpendingOverview(TransactionRepositoryPort transactionRepositoryPort){
        return new GetCardSpendingOverviewService(transactionRepositoryPort);
    }

    @Bean
    GetCardExpenseOverview getCardExpenseOverview(TransactionRepositoryPort transactionRepositoryPort){
        return new GetCardExpenseOverviewService(transactionRepositoryPort);
    }

    @Bean
    GetCardTransactionService getCardTransactionService(TransactionRepositoryPort transactionRepositoryPort,
                                                        TransactionApplicationMapper transactionApplicationMapper){
        return new GetCardTransactionService(transactionRepositoryPort,transactionApplicationMapper);
    }

    @Bean
    GetPaymentMethods getPaymentMethods(CardRepositoryPort cardRepositoryPort){
        return new GetPaymentMethodsService(cardRepositoryPort);
    }
}
