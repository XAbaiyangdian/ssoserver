package com.xaicif.sso.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CheckTicketDto extends BaseSignRequest{

    @NotEmpty(message = "ticket is empty")
    private String ticket;

    @NotEmpty(message = "ssoLogoutCall is empty")
    private String ssoLogoutCall;
}
