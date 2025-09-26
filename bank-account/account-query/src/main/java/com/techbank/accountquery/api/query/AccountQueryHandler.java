package com.techbank.accountquery.api.query;

import com.techbank.accountquery.api.dto.EqualityType;
import com.techbank.accountquery.domain.AccountRepository;
import com.techbank.accountquery.domain.BankAccount;
import com.techbank.cqrs_core.domain.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AccountQueryHandler implements QueryHandler {

    private final AccountRepository accountRepository;

    public AccountQueryHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountsList = new ArrayList<>();
        bankAccounts.forEach(bankAccountsList :: add);
        return bankAccountsList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());

        if(bankAccount.isEmpty())
            return Collections.emptyList();

        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());

        if(bankAccount.isEmpty())
            return Collections.emptyList();

        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountsWithBalanceQuery query) {

        return query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());
    }
}
