package com.application.pennypal.infrastructure.adapter.out.subscription;

import com.application.pennypal.application.dto.input.subscription.SubscriptionPlanInputModel;
import com.application.pennypal.application.dto.output.payment.PaymentResultOutputModel;
import com.application.pennypal.application.dto.output.subscription.PlanOutputModel;
import com.application.pennypal.application.dto.output.subscription.UserSubscriptionOutputModel;
import com.application.pennypal.application.port.in.subscription.AddSubscriptionPlan;
import com.application.pennypal.application.port.in.subscription.DeleteSubscriptionPlan;
import com.application.pennypal.application.port.in.subscription.EditSubscriptionPlan;
import com.application.pennypal.application.port.in.subscription.PurchasePlan;
import com.application.pennypal.application.port.out.repository.SubscriptionPlanRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.out.service.PaymentServicePort;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionInfraService {
    private final PurchasePlan purchasePlan;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final PaymentServicePort paymentServicePort;
    private final SubscriptionPlanRepositoryPort subscriptionPlanRepositoryPort;
    private final AddSubscriptionPlan addSubscriptionPlan;
    private final EditSubscriptionPlan editSubscriptionPlan;
    private final DeleteSubscriptionPlan subscriptionPlan;

    @Transactional
    public PaymentResultOutputModel purchasePlan(String userId,String planId){
        PaymentResultOutputModel outputModel = paymentServicePort.chargeUser(userId,planId);
        UserSubscriptionOutputModel subscription = purchasePlan.execute(userId,planId);
        Transaction transaction = Transaction.create(
                userId,
                null,
                null,
                planId,
                outputModel.price(),
                TransactionType.SUBSCRIPTION,
                "Subscription purchase",
                "Plan: " + subscription.planId(),
                PaymentMethod.STRIPE,
                LocalDate.now(),
                false,
                null,
                null,
                null,
                null
        );
        transactionRepositoryPort.save(transaction);
        return outputModel;
    }

    public List<PlanOutputModel> getPlans(){
        return subscriptionPlanRepositoryPort.getAllPlan().stream()
                .map(
                        plan -> new PlanOutputModel(
                                plan.getPlanId(),
                                plan.getName(),
                                plan.getDescription(),
                                plan.getAmount(),
                                plan.getDurationDays(),
                                plan.getFeatures(),
                                plan.getCreatedAt()
                        )
                )
                .toList();
    }

    public PlanOutputModel add(SubscriptionPlanInputModel inputModel){
        return addSubscriptionPlan.execute(inputModel);
    }

    public PlanOutputModel edit(String planId,SubscriptionPlanInputModel subscriptionPlanInputModel) {
        return editSubscriptionPlan.edit(planId,subscriptionPlanInputModel);
    }

    public PlanOutputModel delete(String planId) {
        return subscriptionPlan.execute(planId);
    }

}
