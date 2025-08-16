package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.mappers.transaction.TransactionApplicationMapper;
import com.application.pennypal.application.port.in.wallet.AddMoneyToWallet;
import com.application.pennypal.application.port.in.wallet.GetWallet;
import com.application.pennypal.application.port.in.wallet.GetWalletStats;
import com.application.pennypal.application.port.in.wallet.GetWalletTransactions;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.WalletRepositoryPort;
import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.application.service.wallet.AddMoneyToWalletService;
import com.application.pennypal.application.service.wallet.GetWalletService;
import com.application.pennypal.application.service.wallet.GetWalletStatsService;
import com.application.pennypal.application.service.wallet.GetWalletTransactionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletBeanConfig {
    @Bean
    public GetWallet getWallet(WalletRepositoryPort walletRepositoryPort){
        return new GetWalletService(walletRepositoryPort);
    }

    @Bean
    public GetWalletStats getWalletStats(TransactionRepositoryPort transactionRepositoryPort){
        return new GetWalletStatsService(transactionRepositoryPort);
    }

    @Bean
    public GetWalletTransactions getWalletTransactions(TransactionRepositoryPort transactionRepositoryPort, TransactionApplicationMapper mapper){
        return new GetWalletTransactionsService(transactionRepositoryPort,mapper);
    }

    @Bean
    public AddMoneyToWallet addMoneyToWallet(
            CardRepositoryPort cardRepositoryPort,
            TransactionRepositoryPort transactionRepositoryPort,
            WalletRepositoryPort walletRepositoryPort,
            EncodePasswordPort encodePasswordPort){
        return new AddMoneyToWalletService(walletRepositoryPort,transactionRepositoryPort,encodePasswordPort,cardRepositoryPort);
    }
}
