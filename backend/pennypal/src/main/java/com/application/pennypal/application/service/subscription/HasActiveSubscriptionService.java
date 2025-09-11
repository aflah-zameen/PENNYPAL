package com.application.pennypal.application.service.subscription;

import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.in.subscription.HasActiveSubscription;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserSubscriptionRepositoryPort;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;

@RequiredArgsConstructor
public class HasActiveSubscriptionService implements HasActiveSubscription {
    private final UserSubscriptionRepositoryPort subscriptionRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public boolean execute(String email) {
        LocalDate  today = LocalDate.now();
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        return subscriptionRepositoryPort.hasActiveSubscription(user.getUserId(),today);
    }
}
