package com.techbank.accountquery.api.dto;

import com.techbank.account_common.dto.BaseResponse;
import com.techbank.accountquery.domain.BankAccount;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountLookupResponse extends BaseResponse {
    private List<BankAccount> accounts;

    public AccountLookupResponse(String message){
        super(message);
    }
}
