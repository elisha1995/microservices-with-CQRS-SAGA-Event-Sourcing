package com.techbank.account_cmd.api.command;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
    void handle(DepositFundCommand command);
    void handle(WithdrawFundCommand command);
    void handle(CloseAccountCommand command);
}
