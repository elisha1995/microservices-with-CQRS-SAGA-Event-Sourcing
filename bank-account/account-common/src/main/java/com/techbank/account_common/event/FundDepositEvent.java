package com.techbank.account_common.event;

import com.techbank.cqrs_core.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundDepositEvent extends BaseEvent {
    private double amount;
}
