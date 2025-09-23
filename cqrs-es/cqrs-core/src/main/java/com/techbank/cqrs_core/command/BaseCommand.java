package com.techbank.cqrs_core.command;

import com.techbank.cqrs_core.message.Message;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class BaseCommand extends Message {
    protected BaseCommand(String id) {
        super(id);
    }
}
