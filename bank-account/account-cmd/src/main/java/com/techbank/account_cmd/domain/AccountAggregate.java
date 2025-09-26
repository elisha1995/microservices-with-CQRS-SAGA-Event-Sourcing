package com.techbank.account_cmd.domain;

import com.techbank.account_cmd.api.command.OpenAccountCommand;
import com.techbank.account_common.event.AccountClosedEvent;
import com.techbank.account_common.event.AccountOpenedEvent;
import com.techbank.account_common.event.FundDepositEvent;
import com.techbank.account_common.event.FundWithdrawnEvent;
import com.techbank.cqrs_core.domain.AggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    @Getter
    private Boolean active;

    @Getter
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdAt((new Date()))
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be deposited into a closed account");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("The deposit amount must be greater than zero");
        }
        raiseEvent(FundDepositEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundDepositEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be withdrawn from a closed account");
        }
        raiseEvent(FundWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundWithdrawnEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!this.active) {
            throw new IllegalStateException("The account is already closed");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());

    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
