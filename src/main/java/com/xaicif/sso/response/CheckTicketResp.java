package com.xaicif.sso.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckTicketResp {

    private String userId;

    private String mobile;

    private String cfcaKeyId;


    public CheckTicketResp(String userId, String mobile, String cfcaKeyId) {
        this.userId = userId;
        this.mobile = mobile;
        this.cfcaKeyId = cfcaKeyId;
    }
}
