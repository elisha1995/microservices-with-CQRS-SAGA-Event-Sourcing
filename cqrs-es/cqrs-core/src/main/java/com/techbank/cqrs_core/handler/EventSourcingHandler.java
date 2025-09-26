package com.techbank.cqrs_core.handler;

import com.techbank.cqrs_core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);
    T getById(String id);
    void republishEvents();
}
