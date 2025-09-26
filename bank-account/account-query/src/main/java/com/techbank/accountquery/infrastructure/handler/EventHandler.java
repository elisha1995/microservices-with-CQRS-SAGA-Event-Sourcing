package com.techbank.accountquery.infrastructure.handler;

import com.techbank.account_common.event.AccountClosedEvent;
import com.techbank.account_common.event.AccountOpenedEvent;
import com.techbank.account_common.event.FundDepositEvent;
import com.techbank.account_common.event.FundWithdrawnEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);
    void on(FundDepositEvent event);
    void on(FundWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
