package com.techbank.account_cmd.infrastructure;

import com.techbank.cqrs_core.command.BaseCommand;
import com.techbank.cqrs_core.command.CommandHandlerMethod;
import com.techbank.cqrs_core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> commandType, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(commandType,
                c -> new ArrayList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if (handlers == null || handlers.isEmpty()) {
            throw new RuntimeException("No handlers registered for command " + command.getClass().getName());
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Multiple handlers registered for command. Can't send command to more than" +
                    " one handler " + command.getClass().getName());
        }
        handlers.getFirst().handle(command);
    }
}
