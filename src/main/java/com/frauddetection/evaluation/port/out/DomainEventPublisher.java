package com.frauddetection.evaluation.port.out;

import com.frauddetection.shared.domain.event.DomainEvent;

public interface DomainEventPublisher {

    void publish(DomainEvent event);
}
