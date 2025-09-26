package com.techbank.account_cmd.infrastructure;

import com.techbank.account_cmd.domain.AccountAggregate;
import com.techbank.cqrs_core.domain.AggregateRoot;
import com.techbank.cqrs_core.event.BaseEvent;
import com.techbank.cqrs_core.handler.EventSourcingHandler;
import com.techbank.cqrs_core.infrastructure.EventStore;
import com.techbank.cqrs_core.producer.EventProducer;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

    private final EventProducer eventProducer;

    public AccountEventSourcingHandler(EventStore eventStore, EventProducer eventProducer) {
        this.eventStore = eventStore;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(),
                aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);

        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(
                    BaseEvent::getVersion).max(Comparator.naturalOrder());
            latestVersion.ifPresent(aggregate::setVersion);
        }

        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds) {
            var aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.getActive()) continue;

            var events = eventStore.getEvents(aggregateId);
            for (var event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
