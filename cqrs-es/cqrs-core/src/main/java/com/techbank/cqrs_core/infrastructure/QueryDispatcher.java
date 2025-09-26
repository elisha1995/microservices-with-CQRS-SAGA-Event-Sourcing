package com.techbank.cqrs_core.infrastructure;

import com.techbank.cqrs_core.domain.BaseEntity;
import com.techbank.cqrs_core.query.BaseQuery;
import com.techbank.cqrs_core.query.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {

    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);

    <U extends BaseEntity> List<U> send(BaseQuery query);
}
