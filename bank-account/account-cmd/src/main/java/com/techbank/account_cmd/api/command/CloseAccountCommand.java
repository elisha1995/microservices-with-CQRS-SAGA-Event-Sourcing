package com.techbank.account_cmd.api.command;

import com.techbank.cqrs_core.command.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
