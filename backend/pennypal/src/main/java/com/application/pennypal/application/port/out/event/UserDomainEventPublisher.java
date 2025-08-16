package com.application.pennypal.application.port.out.event;

import com.application.pennypal.domain.user.event.UserRegisteredEvent;

public interface UserDomainEventPublisher {
    void publish(UserRegisteredEvent userRegisteredEvent);
}
