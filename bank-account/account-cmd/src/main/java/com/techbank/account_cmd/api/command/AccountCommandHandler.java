package com.techbank.account_cmd.api.command;

import com.techbank.account_cmd.domain.AccountAggregate;
import com.techbank.cqrs_core.handler.EventSourcingHandler;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler{

    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    public AccountCommandHandler(EventSourcingHandler<AccountAggregate> eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        if (command.getAmount() > aggregate.getBalance()) {
            throw new IllegalStateException("Withdrawal declined. Insufficient funds");
        }
        aggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }
}
