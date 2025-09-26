package com.techbank.cqrs_core.producer;

import com.techbank.cqrs_core.event.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
