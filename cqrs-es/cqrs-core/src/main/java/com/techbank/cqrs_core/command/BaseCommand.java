package com.techbank.cqrs_core.command;

import com.techbank.cqrs_core.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseCommand extends Message {
    protected BaseCommand(String id) {
        super(id);
    }
}
