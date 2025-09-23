package com.techbank.cqrs_core.event;

import com.techbank.cqrs_core.message.Message;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEvent extends Message {
    private int version;
}
