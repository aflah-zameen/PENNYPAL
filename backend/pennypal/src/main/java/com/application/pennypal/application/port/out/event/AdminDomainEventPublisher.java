package com.application.pennypal.application.port.out.event;

import com.application.pennypal.domain.user.event.AdminRegisteredEvent;

public interface AdminDomainEventPublisher {
    void publish(AdminRegisteredEvent adminRegisteredEvent);
}
