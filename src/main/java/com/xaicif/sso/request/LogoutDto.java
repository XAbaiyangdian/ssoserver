package com.xaicif.sso.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class LogoutDto extends BaseSignRequest {

    @NotEmpty(message = "userId is empty")
    private String userId;

    public LogoutDto(String userId) {
        this.userId = userId;
    }
}
