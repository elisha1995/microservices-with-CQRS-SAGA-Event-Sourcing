package com.techbank.cqrs_core.infrastructure;

import com.techbank.cqrs_core.command.BaseCommand;
import com.techbank.cqrs_core.command.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> commandType, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
