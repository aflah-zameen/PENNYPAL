package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.mappers.lent.LentApplicationMapper;
import com.application.pennypal.application.mappers.lent.LoanCaseApplicationMapper;
import com.application.pennypal.application.port.in.lent.*;
import com.application.pennypal.application.port.in.transaction.GetAllLoanCases;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.LoanCaseRepositoryPort;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.application.service.coin.CoinRewardService;
import com.application.pennypal.application.service.lent.*;
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

//    @Bean
//    public UpdateLendingRequestStatus updateLendingRequestStatus(LendingRequestRepositoryPort lendingRequestRepositoryPort,
//                                                                 UserRepositoryPort userRepositoryPort,
//                                                                 MessageBrokerPort messageBrokerPort){
//        return new UpdateLendingRequestStatusService(lendingRequestRepositoryPort,messageBrokerPort,userRepositoryPort);
//    }

    @Bean
    public GetLendingRequestsSent getLendingRequestsSent(LendingRequestRepositoryPort lendingRequestRepositoryPort,
                                                         LentApplicationMapper applicationMapper){
        return new GetLendingRequestsSentService(lendingRequestRepositoryPort,applicationMapper);
    }

    @Bean
    public GetLendingRequestReceived getLendingRequestsReceived(LendingRequestRepositoryPort lendingRequestRepositoryPort,
                                                            LentApplicationMapper applicationMapper){
        return new GetLendingRequestReceivedService(lendingRequestRepositoryPort,applicationMapper);
    }

    @Bean
    public GetLendingSummary getLendingSummary(LendingRequestRepositoryPort lendingRequestRepositoryPort, LoanRepositoryPort loanRepositoryPort){
        return new GetLendingSummaryService(lendingRequestRepositoryPort,loanRepositoryPort);
    }

    @Bean
    public GetLendingRequestsSent lendingRequestsSent(LendingRequestRepositoryPort lendingRequestRepositoryPort,LentApplicationMapper lentApplicationMapper){
        return new GetLendingRequestsSentService(lendingRequestRepositoryPort,lentApplicationMapper);
    }

    @Bean
    public GetLendingRequestReceived getLendingRequestReceived(LendingRequestRepositoryPort lendingRequestRepositoryPort,LentApplicationMapper lentApplicationMapper){
        return new GetLendingRequestReceivedService(lendingRequestRepositoryPort,lentApplicationMapper);
    }

    @Bean
    public GetLoansToRepay getLoansToRepay(LoanRepositoryPort loanRepositoryPort,LentApplicationMapper lentApplicationMapper){
        return new GetLoansToRepayService(loanRepositoryPort,lentApplicationMapper);
    }

    @Bean
    public GetLoansToCollect getLoansToCollect(LoanRepositoryPort loanRepositoryPort,LentApplicationMapper lentApplicationMapper){
        return new GetLoansToCollectService(loanRepositoryPort,lentApplicationMapper);
    }

    @Bean
    public ApproveLendingRequest approveLendingRequest(LendingRequestRepositoryPort lendingRequestRepositoryPort,LoanRepositoryPort loanRepositoryPort){
        return new ApproveLendingRequestService(lendingRequestRepositoryPort,loanRepositoryPort);
    }

    @Bean
    public CancelLendingRequest cancelLendingRequest(LendingRequestRepositoryPort lendingRequestRepositoryPort){
        return new CancelLendingRequestService(lendingRequestRepositoryPort);
    }

    @Bean
    public RejectLendingRequest rejectLendingRequest(LendingRequestRepositoryPort lendingRequestRepository){
        return new RejectLendingRequestService(lendingRequestRepository);
    }

    @Bean
    public RemindLoanPayment remindLoanPayment( LoanRepositoryPort loanRepositoryPort,
   MessageBrokerPort messageBrokerPort,
    UserRepositoryPort userRepositoryPort,
    LendingRequestRepositoryPort lendingRequestRepositoryPort,
    LentApplicationMapper lentApplicationMapper){
        return new RemindLoanPaymentService(loanRepositoryPort,messageBrokerPort,userRepositoryPort,lendingRequestRepositoryPort,lentApplicationMapper);
    }

    @Bean
    public LoanRepayment loanRepayment(LoanRepositoryPort loanRepositoryPort,
                                       CoinRewardService coinRewardService){
        return new LoanRepaymentService(loanRepositoryPort,coinRewardService);
    }

    @Bean
    public FileLoanCase fileLoanCase(LoanCaseRepositoryPort loanCaseRepositoryPort,
                                     LoanRepositoryPort loanRepositoryPort,
                                     LendingRequestRepositoryPort lendingRequestRepositoryPort,
                                     MessageBrokerPort messageBrokerPort,
                                     UserRepositoryPort userRepositoryPort,
                                     LentApplicationMapper lentApplicationMapper){
        return new FileLoanCaseService(loanCaseRepositoryPort,loanRepositoryPort,lendingRequestRepositoryPort,messageBrokerPort,userRepositoryPort,lentApplicationMapper);
    }

    @Bean
    public GetAllLoans getAllLoans(LoanRepositoryPort loanRepositoryPort,LentApplicationMapper lentApplicationMapper){
        return new GetAllLoansService(loanRepositoryPort,lentApplicationMapper);
    }

    @Bean
    public GetLoanAdminSummary getLoanAdminSummary(LoanRepositoryPort loanRepositoryPort){
        return new GetLoanAdminSummaryService(loanRepositoryPort);
    }

    @Bean
    public GetAllLoanCases getAllLoanCases(LoanCaseRepositoryPort loanCaseRepositoryPort, LoanCaseApplicationMapper loanCaseApplicationMapper){
        return new GetAllCasesService(loanCaseRepositoryPort,loanCaseApplicationMapper);
    }

    @Bean
    AdminLoanReminder adminLoanReminder(LoanRepositoryPort loanRepositoryPort,
                                        LendingRequestRepositoryPort lendingRequestRepositoryPort,
                                        UserRepositoryPort userRepositoryPort,
                                        MessageBrokerPort messageBrokerPort){
        return new AdminLoanReminderService(loanRepositoryPort,lendingRequestRepositoryPort,userRepositoryPort,messageBrokerPort);
    }

}
