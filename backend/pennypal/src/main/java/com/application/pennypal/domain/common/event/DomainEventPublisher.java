package com.application.pennypal.domain.common.event;

public interface   DomainEventPublisher<T> {
    void publish(T event);
}
