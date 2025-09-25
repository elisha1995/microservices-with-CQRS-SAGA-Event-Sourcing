package com.techbank.account_cmd.infrastructure;

import com.techbank.account_cmd.domain.AccountAggregate;
import com.techbank.cqrs_core.domain.AggregateRoot;
import com.techbank.cqrs_core.event.BaseEvent;
import com.techbank.cqrs_core.handler.EventSourcingHandler;
import com.techbank.cqrs_core.infrastructure.EventStore;

import java.util.Comparator;

public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

    public AccountEventSourcingHandler(EventStore eventStore) {
        this.eventStore = eventStore;
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
}
