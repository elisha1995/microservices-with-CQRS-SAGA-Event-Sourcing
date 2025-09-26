package com.techbank.account_common.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseResponse {
    private String message;
}
