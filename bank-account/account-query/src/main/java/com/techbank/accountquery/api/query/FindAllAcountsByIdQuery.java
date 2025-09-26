package com.techbank.accountquery.api.query;

import com.techbank.cqrs_core.query.BaseQuery;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class FindAllAcountsByIdQuery extends BaseQuery {

    private String id;
}
