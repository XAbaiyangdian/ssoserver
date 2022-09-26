package com.xaicif.sso.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckTicketResp {

    private String userId;

    private String loginName;

    private String uscc;

    private String mobile;

    private String cfcaKeyId;


    public CheckTicketResp(String userId, String loginName, String uscc, String mobile, String cfcaKeyId) {
        this.userId = userId;
        this.loginName = loginName;
        this.uscc = uscc;
        this.mobile = mobile;
        this.cfcaKeyId = cfcaKeyId;
    }
}
