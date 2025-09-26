package com.techbank.accountquery.infrastructure.handler;

import com.techbank.account_common.event.AccountClosedEvent;
import com.techbank.account_common.event.AccountOpenedEvent;
import com.techbank.account_common.event.FundDepositEvent;
import com.techbank.account_common.event.FundWithdrawnEvent;
import com.techbank.accountquery.domain.AccountRepository;
import com.techbank.accountquery.domain.BankAccount;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler{

    private final AccountRepository accountRepository;

    public AccountEventHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .creationDate(event.getCreatedAt())
                .build();

        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundDepositEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) {
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();
        bankAccount.get().setBalance(latestBalance);

        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FundWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) {
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);

        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
