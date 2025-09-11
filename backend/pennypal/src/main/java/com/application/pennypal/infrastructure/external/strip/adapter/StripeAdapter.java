package com.application.pennypal.infrastructure.external.strip.adapter;

import com.application.pennypal.application.dto.output.payment.PaymentResultOutputModel;
import com.application.pennypal.application.port.out.repository.SubscriptionPlanRepositoryPort;
import com.application.pennypal.application.port.out.service.PaymentServicePort;
import com.application.pennypal.domain.subscription.SubscriptionPlan;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripeAdapter implements PaymentServicePort {
    private final SubscriptionPlanRepositoryPort planRepository;
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    public PaymentResultOutputModel chargeUser(String userId, String planId) {
        // Fetch plan details from repository
        SubscriptionPlan plan = planRepository.getPlan(planId)
                .orElseThrow(() -> new InfrastructureException("Plan not found","NOT_FOUND"));

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", plan.getAmount().multiply(BigDecimal.valueOf(100)).intValue()); // in cents
            params.put("currency", "usd");
            params.put("description", "Subscription Plan: " + plan.getName());

            PaymentIntent intent = PaymentIntent.create(params);

            return new PaymentResultOutputModel(true, intent.getId(), plan.getAmount());
        } catch (StripeException e) {
            return new PaymentResultOutputModel(false, null, plan.getAmount());
        }
    }
}
