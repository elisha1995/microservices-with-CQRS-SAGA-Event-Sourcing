package com.techbank.account_cmd.api.command;

import com.techbank.cqrs_core.command.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundCommand extends BaseCommand {
    private double amount;
}
