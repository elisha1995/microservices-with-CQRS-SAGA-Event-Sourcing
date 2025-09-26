package com.techbank.accountquery.infrastructure.consumer;

import com.techbank.account_common.event.AccountClosedEvent;
import com.techbank.account_common.event.AccountOpenedEvent;
import com.techbank.account_common.event.FundDepositEvent;
import com.techbank.account_common.event.FundWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume(@Payload FundDepositEvent event, Acknowledgment ack);
    void consume(@Payload FundWithdrawnEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
