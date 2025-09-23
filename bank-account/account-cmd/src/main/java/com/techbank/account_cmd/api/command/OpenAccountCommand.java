package com.techbank.account_cmd.api.command;

import com.techbank.account_common.dto.AccountType;
import com.techbank.cqrs_core.command.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
