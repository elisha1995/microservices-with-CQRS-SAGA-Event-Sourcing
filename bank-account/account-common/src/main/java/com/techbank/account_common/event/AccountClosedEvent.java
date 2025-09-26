package com.techbank.account_common.event;

import com.techbank.cqrs_core.event.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AccountClosedEvent extends BaseEvent {
}
