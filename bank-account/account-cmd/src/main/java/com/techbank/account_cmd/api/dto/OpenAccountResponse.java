package com.techbank.account_cmd.api.dto;

import com.techbank.account_common.dto.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OpenAccountResponse extends BaseResponse {
    private String id;

    public OpenAccountResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
