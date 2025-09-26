package com.techbank.account_common.event;

import com.techbank.cqrs_core.event.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FundDepositEvent extends BaseEvent {
    private double amount;
}
