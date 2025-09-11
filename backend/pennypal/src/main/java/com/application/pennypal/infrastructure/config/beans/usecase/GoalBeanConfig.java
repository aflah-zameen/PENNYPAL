package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.mappers.goal.GoalApplicationMapper;
import com.application.pennypal.application.mappers.goal.GoalContributionApplicationMapper;
import com.application.pennypal.application.port.in.coin.CoinReward;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.application.port.out.repository.*;
import com.application.pennypal.application.port.in.goal.*;
import com.application.pennypal.application.service.goal.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoalBeanConfig {

    @Bean
    public AddGoal addGoal(GoalApplicationMapper goalApplicationMapper,
                           GoalRepositoryPort goalRepositoryPort,
                           TransactionRepositoryPort transactionRepositoryPort){
        return new AddGoalService(
                goalApplicationMapper,
                goalRepositoryPort,
                transactionRepositoryPort
        );
    }
    @Bean
    public GoalApplicationMapper goalApplicationMapper(CategoryManagementRepositoryPort categoryManagementRepositoryPort,
                                                       GoalContributionApplicationMapper goalContributionApplicationMapper,
                                                       UserCoinAccountRepositoryPort userCoinAccountRepositoryPort){
        return new GoalApplicationMapper(categoryManagementRepositoryPort,goalContributionApplicationMapper,userCoinAccountRepositoryPort);
    }

    @Bean
    public GetAllGoals getAllGoals(GoalRepositoryPort goalRepositoryPort, GoalApplicationMapper goalApplicationMapper,GoalContributionRepositoryPort goalContributionRepositoryPort){
        return new GetAllGoalsService(goalRepositoryPort,goalApplicationMapper,goalContributionRepositoryPort);
    }

    @Bean
    public AddContribution addContribution(GoalRepositoryPort goalRepositoryPort,
                                           TransactionRepositoryPort transactionRepositoryPort,
                                           GoalContributionRepositoryPort goalContributionRepositoryPort,
                                           GoalContributionApplicationMapper goalContributionApplicationMapper,
                                           CardRepositoryPort cardRepositoryPort,
                                           CoinReward coinReward){
        return new AddContributionService(goalRepositoryPort,transactionRepositoryPort,goalContributionRepositoryPort,goalContributionApplicationMapper,cardRepositoryPort,coinReward);
    }
    @Bean
    public EditGoal editGoal(GoalRepositoryPort goalRepositoryPort, GoalApplicationMapper goalApplicationMapper){
        return new EditGoalService(goalRepositoryPort,goalApplicationMapper);
    }

    @Bean
    public DeleteGoal deleteGoal(GoalRepositoryPort goalRepositoryPort){
        return new DeleteGoalService(goalRepositoryPort);
    }

    @Bean
    public GetGoalSummary getGoalSummary(GoalRepositoryPort goalRepositoryPort){
        return new GetGoalSummaryService(goalRepositoryPort);
    }

    @Bean
    public GoalWithdrawRequest goalWithdrawRequest(GoalRepositoryPort goalRepositoryPort,
                                                   GoalWithdrawRepositoryPort withdrawRepositoryPort,
                                                   UserRepositoryPort userRepositoryPort,
                                                   MessageBrokerPort messageBrokerPort,
                                                   NotificationRepositoryPort notificationRepositoryPort
                                                   ){
        return new GoalWithdrawRequestService(withdrawRepositoryPort,goalRepositoryPort,userRepositoryPort,messageBrokerPort,notificationRepositoryPort);
    }

    @Bean
    public GetAdminGoalData getAdminGoalData(GoalRepositoryPort goalRepositoryPort){
        return new GetAdminGoalDataService(goalRepositoryPort);
    }

    @Bean
    public GetAdminGoalStats getAdminGoalStats(GoalRepositoryPort goalRepositoryPort,
                                               GoalWithdrawRepositoryPort goalWithdrawRepositoryPort){
        return new GetAdminGoalStatsService(goalRepositoryPort,goalWithdrawRepositoryPort);
    }

    @Bean
    public GoalWithdrawalApproval goalWithdrawalApproval(GoalRepositoryPort goalRepositoryPort,
                                                         GoalWithdrawRepositoryPort goalWithdrawRepositoryPort,
                                                         WalletRepositoryPort walletRepositoryPort,
                                                         MessageBrokerPort messageBrokerPort,
                                                         UserRepositoryPort userRepositoryPort,
                                                         TransactionRepositoryPort transactionRepositoryPort){
        return new GoalWithdrawalApprovalService(goalWithdrawRepositoryPort,goalRepositoryPort,walletRepositoryPort,messageBrokerPort,userRepositoryPort,transactionRepositoryPort);
    }

    @Bean
    public GoalWithdrawalRejection goalWithdrawalRejection(GoalWithdrawRepositoryPort withdrawRepositoryPort,
                                                           GoalRepositoryPort goalRepositoryPort,
                                                           NotificationRepositoryPort notificationRepositoryPort,
                                                           UserRepositoryPort userRepositoryPort,
                                                           MessageBrokerPort messageBrokerPort) {
        return new GoalWithdrawalRejectionService(goalRepositoryPort,withdrawRepositoryPort,
                userRepositoryPort,messageBrokerPort,notificationRepositoryPort);
    }

    @Bean
    public GetAllGoalWithdrawRequests getAllGoalWithdrawRequests(GoalWithdrawRepositoryPort goalWithdrawRepositoryPort) {
        return new GetAllGoalWithdrawRequestsService(goalWithdrawRepositoryPort);
    }

}
