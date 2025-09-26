package com.techbank.accountquery.api.query;

import com.techbank.accountquery.api.dto.EqualityType;
import com.techbank.cqrs_core.query.BaseQuery;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class FindAccountsWithBalanceQuery extends BaseQuery {

    private EqualityType equalityType;
    private double balance;
}
