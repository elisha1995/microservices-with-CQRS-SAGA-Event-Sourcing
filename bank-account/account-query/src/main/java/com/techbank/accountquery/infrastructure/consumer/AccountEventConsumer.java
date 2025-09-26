package com.techbank.accountquery.infrastructure.consumer;

import com.techbank.account_common.event.AccountClosedEvent;
import com.techbank.account_common.event.AccountOpenedEvent;
import com.techbank.account_common.event.FundDepositEvent;
import com.techbank.account_common.event.FundWithdrawnEvent;
import com.techbank.accountquery.infrastructure.handler.EventHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumer{

    private final EventHandler eventHandler;

    public AccountEventConsumer(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload AccountOpenedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundDepositEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FundDepositEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FundWithdrawnEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload AccountClosedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }
}
