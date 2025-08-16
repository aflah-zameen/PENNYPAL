package com.application.pennypal.infrastructure.event.publisher;

import com.application.pennypal.domain.common.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class SpringDomainEventPublisher <T> implements DomainEventPublisher<T> {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(T event) {
        applicationEventPublisher.publishEvent(event);
    }
}
