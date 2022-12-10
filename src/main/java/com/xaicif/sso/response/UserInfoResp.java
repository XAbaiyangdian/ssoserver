package com.xaicif.sso.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoResp {

    private String userId;

    private String loginName;

    private String mobile;

    private String cfcaKeyId;

    private String company;

    private String uscc;

    private String realName;

    private String idCard;

    private String companyRole;

    public UserInfoResp(String userId, String loginName, String mobile, String cfcaKeyId, String company, String uscc, String realName, String idCard, String companyRole) {
        this.userId = userId;
        this.loginName = loginName;
        this.company = company;
        this.mobile = mobile;
        this.cfcaKeyId = cfcaKeyId;
        this.uscc = uscc;
        this.realName = realName;
        this.idCard = idCard;
        this.companyRole = companyRole;
    }
}
