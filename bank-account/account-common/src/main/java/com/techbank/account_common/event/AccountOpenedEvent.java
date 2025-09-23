package com.techbank.account_common.event;

import com.techbank.account_common.dto.AccountType;
import com.techbank.cqrs_core.event.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AccountOpenedEvent extends BaseEvent {
    private String accountHolder;
    private AccountType accountType;
    private Date createdAt;
    private double openingBalance;
}
